package gr.aueb.network_book.book;

import gr.aueb.network_book.common.PageResponse;
import gr.aueb.network_book.exception.OperationNotPermittedException;
import gr.aueb.network_book.file.FileStorageService;
import gr.aueb.network_book.history.BookTransactionHistory;
import gr.aueb.network_book.history.IBookTransactionalRepository;
import gr.aueb.network_book.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final IBookRepository bookRepository;
    private final BookMapper bookMapper;
    private final IBookTransactionalRepository transactionalRepository;
    private final FileStorageService fileStorageService;


    public Integer save(BookRequest req, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Book book = bookMapper.toBook(req);
        book.setOwner(user);

        return bookRepository.save(book).getId();
    }

    public BookResponse findById(Integer bookId) {
        return bookRepository.findById(bookId)
                .map(bookMapper::toBookResponse)
                .orElseThrow(() -> new EntityNotFoundException("Book with id: " + bookId + " not found"));

    }

    public PageResponse<BookResponse> findAllBooks(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAllDisplayedBooks(pageable, user.getId());
        List<BookResponse> bookResponseList = books.stream().map(bookMapper::toBookResponse).toList();
        return new PageResponse<>(
                bookResponseList,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()
        );
    }


    public PageResponse<BookResponse> findAllBooksByOwner(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAll(BookSpecification.ownerId(user.getId()), pageable);

        List<BookResponse> bookResponseList = books.stream().map(bookMapper::toBookResponse).toList();
        return new PageResponse<>(
                bookResponseList,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()
        );
    }

    public PageResponse<BorrowedBookResponse> findAllBorrowedBooks(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<BookTransactionHistory> allBorrowedBooks = transactionalRepository.findAllBorrowedBooks(pageable, user.getId());
        List<BorrowedBookResponse> bookResponse = allBorrowedBooks.stream().map(bookMapper::toBorrowedBookResponse).toList();

        return new PageResponse<>(
                bookResponse,
                allBorrowedBooks.getNumber(),
                allBorrowedBooks.getSize(),
                allBorrowedBooks.getTotalElements(),
                allBorrowedBooks.getTotalPages(),
                allBorrowedBooks.isFirst(),
                allBorrowedBooks.isLast()
        );
    }

    public PageResponse<BorrowedBookResponse> findAllReturnedBooks(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<BookTransactionHistory> allBorrowedBooks = transactionalRepository.findAllReturnedBooks(pageable, connectedUser.getName());
        List<BorrowedBookResponse> bookResponse = allBorrowedBooks.stream().map(bookMapper::toBorrowedBookResponse).toList();

        return new PageResponse<>(
                bookResponse,
                allBorrowedBooks.getNumber(),
                allBorrowedBooks.getSize(),
                allBorrowedBooks.getTotalElements(),
                allBorrowedBooks.getTotalPages(),
                allBorrowedBooks.isFirst(),
                allBorrowedBooks.isLast()
        );
    }

    public Integer updateShareableStatus(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No book found for ID: " + bookId));
        User user = ((User) connectedUser.getPrincipal());
        if (!Objects.equals(book.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("Update book status is not available");
        }
        book.setShareable(!book.isShareable());
        bookRepository.save(book);
        return bookId;
    }

    public Integer updateArchivedStatus(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No book found for ID: " + bookId));
        User user = ((User) connectedUser.getPrincipal());
        if (!Objects.equals(book.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("Archive book status is not available");
        }
        book.setArchived(!book.isArchived());
        bookRepository.save(book);
        return bookId;

    }

    public Integer borrowBook(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID:: " + bookId));
        if (book.isArchived() || !book.isShareable()) {
            throw new OperationNotPermittedException("The requested book cannot be borrowed since it is archived or not shareable");
        }
        User user = ((User) connectedUser.getPrincipal());
        if (Objects.equals(book.getCreatedBy(), connectedUser.getName())) {
            throw new OperationNotPermittedException("You cannot borrow your own book");
        }
        final boolean isAlreadyBorrowedByUser = transactionalRepository.isAlreadyBorrowedByUser(bookId, user.getId());
        if (isAlreadyBorrowedByUser) {
            throw new OperationNotPermittedException("You already borrowed this book and it is still not returned or the return is not approved by the owner");
        }

//        final boolean isAlreadyBorrowedByOtherUser = transactionalRepository.isAlreadyBorrowed(bookId);
//        if (isAlreadyBorrowedByOtherUser) {
//            throw new OperationNotPermittedException("The requested book is already borrowed");
//        }

        BookTransactionHistory bookTransactionHistory = BookTransactionHistory.builder()
                .user(user)
                .book(book)
                .returned(false)
                .returnApproved(false)
                .build();
        return transactionalRepository.save(bookTransactionHistory).getId();
    }

    public Integer returnBorrowedBook(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID:: " + bookId));
        if (book.isArchived() || !book.isShareable()) {
            throw new OperationNotPermittedException("The requested book cannot be borrowed since it is archived or not shareable");
        }
            User user = ((User) connectedUser.getPrincipal());
            if (Objects.equals(book.getCreatedBy(), connectedUser.getName())) {
                throw new OperationNotPermittedException("You cannot borrow or return your own book");
            }
            BookTransactionHistory bookTransactionHistory = transactionalRepository.findByBookIdAndUserId(bookId, user.getId())
                    .orElseThrow(() -> new OperationNotPermittedException("You did not borrow this book"));
            bookTransactionHistory.setReturned(true);
            return transactionalRepository.save(bookTransactionHistory).getId();
        }


        public Integer approveReturnBorrowedBook (Integer bookId, Authentication connectedUser){
            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new EntityNotFoundException("No book found with ID:: " + bookId));
            if (book.isArchived() || !book.isShareable()) {
                throw new OperationNotPermittedException("The requested book cannot be borrowed since it is archived or not shareable");
            }
                User user = ((User) connectedUser.getPrincipal());
                if (Objects.equals(book.getCreatedBy(), connectedUser.getName())) {
                    throw new OperationNotPermittedException("You cannot borrow or return your own book");
                }
                BookTransactionHistory bookTransactionHistory = transactionalRepository.findByBookIdAndOwnerId(bookId, user.getId())
                        .orElseThrow(() -> new OperationNotPermittedException("The book is not returned yet"));
                bookTransactionHistory.setReturnApproved(true);
                return transactionalRepository.save(bookTransactionHistory).getId();
            }

    public void uploadBookCoverPicture(MultipartFile file, Authentication connectedUser, Integer bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID:: " + bookId));
         User user = ((User) connectedUser.getPrincipal());
        var profilePicture = fileStorageService.saveFile(file , user.getId());
        book.setBookCover(profilePicture);
        bookRepository.save(book);
    }
}
