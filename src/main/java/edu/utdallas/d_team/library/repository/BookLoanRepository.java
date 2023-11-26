package edu.utdallas.d_team.library.repository;

import edu.utdallas.d_team.library.entity.BookLoan;
import edu.utdallas.d_team.library.entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookLoanRepository extends JpaRepository<BookLoan, Integer> {

    List<BookLoan> findByBook_IsbnAndDateInIsNull(String isbn);

    List<BookLoan> findByBorrowerAndDateInNull(Borrower borrower);





    List<BookLoan> findByBorrower_CardId(String cardId);

    List<BookLoan> findByBorrowerAndDateInIsNull(Borrower borrower);
}