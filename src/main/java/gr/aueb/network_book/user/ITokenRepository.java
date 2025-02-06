package gr.aueb.network_book.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ITokenRepository extends JpaRepository<Token , Integer> {

    Optional<Token> findByToken (String token);
}
