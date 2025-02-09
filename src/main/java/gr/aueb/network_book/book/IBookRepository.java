package gr.aueb.network_book.book;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IBookRepository extends JpaRepository<Book , Integer> {
}
