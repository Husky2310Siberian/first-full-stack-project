package gr.aueb.network_book.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User , Integer> {

    Optional<User> findUserByEmail(String email);
}
