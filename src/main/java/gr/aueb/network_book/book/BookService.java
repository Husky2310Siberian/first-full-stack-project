package gr.aueb.network_book.book;

import gr.aueb.network_book.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final IBookRepository bookRepository;
    private final BookMapper bookMapper;


    public Integer save(BookRequest req, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Book book = bookMapper.toBook(req);
        book.setOwner(user);

        return bookRepository.save(book).getId();
    }
}
