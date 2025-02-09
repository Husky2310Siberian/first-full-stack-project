package gr.aueb.network_book.book;

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
}
