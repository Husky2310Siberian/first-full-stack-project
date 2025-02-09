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

    @Builder  // Add this annotation
    public Token(String token, LocalDateTime createdAt, LocalDateTime expiresAt, User user) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.user = user;
    }

    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime validatesAt;

    @ManyToOne
    @JoinColumn(name = "userId" , nullable = false)
    private User user;
}
