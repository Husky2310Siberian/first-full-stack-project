package gr.aueb.network_book.book;

import gr.aueb.network_book.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Entity tracks records about books ratings
 */

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Feedback extends BaseEntity {

    private Double note;  //1 - 5 stars
    private String comment;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
