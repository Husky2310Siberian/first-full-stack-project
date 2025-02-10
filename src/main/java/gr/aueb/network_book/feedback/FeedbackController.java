package gr.aueb.network_book.feedback;

import gr.aueb.network_book.common.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name= "Feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<Integer> saveFeedback(@Valid @RequestBody FeedbackRequest req , Authentication connectedUser){
        return ResponseEntity.ok(feedbackService.save(req , connectedUser));
    }

    @GetMapping("/book/{book-id}")
    public ResponseEntity<PageResponse<FeedbackResponse>> findAllFeedbackBook(@PathVariable("book-id") Integer bookId,
                                                                              @RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                                              @RequestParam(name = "size", defaultValue = "10", required = false) int size,
                                                                              Authentication connectedUser) {

        return ResponseEntity.ok(feedbackService.findAllFeedbacksByBook(bookId , page , size , connectedUser));
    }

}
