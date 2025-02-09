package gr.aueb.network_book.history;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IBookTransactionalRepository extends JpaRepository <BookTransactionHistory , Integer> {

    @Query("""
            SELECT history
            FROM BookTransactionHistory history
            WHERE history.user.id = :userId
            """)

    Page<BookTransactionHistory> findAllBorrowedBooks(Pageable pageable, Integer userId);


    @Query("""
            SELECT history
            FROM BookTransactionHistory history
            WHERE history.book.owner.id = :userId
            """)
    Page<BookTransactionHistory> findAllReturnedBooks(Pageable pageable, String userId);


    @Query("""
            SELECT 
            (COUNT(*) > 0) AS isBorrowed
            FROM BookTransactionHistory bookTransactionalHistory
            WHERE bookTransactionalHistory.user.id = :userId
            AND bookTransactionalHistory.book.id = :bookId
            AND bookTransactionalHistory.returnApproved = false
            """)
    boolean isAlreadyBorrowedByUser(Integer bookId, Integer id);

    @Query("""
            SELECT transaction
            FROM BookTransactionHistory transaction
            WHERE transaction.user.id = :userId
            AND transaction.book.id = :bookId
            AND transaction.returned = false 
            AND transaction.returnApproved = false 
            """)
    Optional<BookTransactionHistory> findByBookIdAndUserId(Integer bookId, Integer userId);


    @Query("""
            SELECT transaction
            FROM BookTransactionHistory transaction
            WHERE transaction.book.owner.id = :userId
            AND transaction.book.id = :bookId
            AND transaction.returned = true 
            AND transaction.returnApproved = false 
            """)
    Optional<BookTransactionHistory> findByBookIdAndOwnerId(Integer bookId, Integer id);
}


