package gr.aueb.network_book.book;

import gr.aueb.network_book.common.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
@Tag(name = "Book")
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<Integer> saveBook(
            @Valid @RequestBody BookRequest req,
            Authentication connectedUser)
    {
        return ResponseEntity.ok(bookService.save(req, connectedUser));
    }

    @GetMapping
    public ResponseEntity<BookResponse> findBooks(@PathVariable ("book_id")  Integer bookId) {

        return ResponseEntity.ok(bookService.findById(bookId));
    }

    @GetMapping("{book-id}")
    public ResponseEntity<PageResponse<BookResponse>> findAllBooks(@RequestParam (name = "page" , defaultValue = "0" , required = false) int page,
                                                                   @RequestParam (name = "size" , defaultValue = "10", required = false) int size,
                                                                   Authentication connectedUser)
            {

        return ResponseEntity.ok(bookService.findAllBooks(page, size ,connectedUser));
    }

}

