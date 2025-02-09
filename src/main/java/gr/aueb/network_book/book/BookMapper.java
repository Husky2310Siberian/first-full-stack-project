package gr.aueb.network_book.book;

import gr.aueb.network_book.history.BookTransactionHistory;
import org.springframework.stereotype.Service;

@Service
public class BookMapper {
    public Book toBook(BookRequest req) {
        return Book.builder()
                .id(req.id())
                .title(req.title())
                .authorName(req.authorName())
                .synopsis(req.synopsis())
                .archived(false)
                .shareable(req.shareable())
                .build();
    }

    public BookResponse toBookResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .authorName(book.getAuthorName())
                .isbn(book.getIsbn())
                .synopsis(book.getSynopsis())
                .rate(book.getRate())
                .archived(book.isArchived())
                .shareable(book.isShareable())
                .owner(book.getOwner().getFullName())
                .build();
    }

    public BorrowedBookResponse toBorrowedBookResponse(BookTransactionHistory history ) {
        return BorrowedBookResponse.builder()
            .id(history.getBook().getId())
            .title(history.getBook().getTitle())
            .authorName(history.getBook().getAuthorName())
            .isbn(history.getBook().getIsbn())
            .rate(history.getBook().getRate()).
                returned(history.isReturned())
                .returnApproved(history.isReturnApproved())
            .build();
    }
}
