package gr.aueb.network_book.role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Role , Integer> {

    Optional<Role> findByName(String role);
}
