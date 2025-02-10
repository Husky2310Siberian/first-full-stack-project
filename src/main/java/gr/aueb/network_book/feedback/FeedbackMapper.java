package gr.aueb.network_book.feedback;

import gr.aueb.network_book.book.Book;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class FeedbackMapper {
    public Feedback toFeedback(FeedbackRequest req) {
        return Feedback.builder()
                .note(req.note())
                .comment(req.comment())
                .book(Book.builder()
                        .id(req.bookId())
                        .shareable(false)
                        .archived(false)
                        .build()
                )
                .build();
    }

    public FeedbackResponse toFeedbackResponse(Feedback feedback, Integer id) {
        return FeedbackResponse.builder()
                .note(feedback.getNote())
                .comment(feedback.getComment())
                .ownFeedback(Objects.equals(feedback.getCreatedBy(), id))
                .build();
    }
}
