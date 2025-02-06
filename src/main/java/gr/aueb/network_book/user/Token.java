package gr.aueb.network_book.user;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Application will create automatic a token
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Token {

    @Id
    @GeneratedValue
    private Integer id;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime validatesAt;

    @ManyToOne
    @JoinColumn(name = "userId" , nullable = false)
    private User user;
}
