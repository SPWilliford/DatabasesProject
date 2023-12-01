package edu.utdallas.d_team.library.repository;

import edu.utdallas.d_team.library.entity.BookLoan;
import edu.utdallas.d_team.library.entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookLoanRepository extends JpaRepository<BookLoan, Integer> {

    List<BookLoan> findByBook_IsbnAndDateInIsNull(String isbn);

    List<BookLoan> findByBorrowerAndDateInNull(Borrower borrower);

    List<BookLoan> findByBorrower_CardId(String cardId);

    List<BookLoan> findByBorrowerAndDateInIsNull(Borrower borrower);

    Integer countBookLoanByBorrowerAndDateInIsNull(Borrower borrower);


    List<BookLoan> findByBook_Isbn(String isbn);

    List<BookLoan> findByBorrower_BnameLikeIgnoreCase(String bname);



}