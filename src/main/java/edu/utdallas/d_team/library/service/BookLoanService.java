package edu.utdallas.d_team.library.service;

import edu.utdallas.d_team.library.entity.BookLoan;
import edu.utdallas.d_team.library.entity.Borrower;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface BookLoanService {

    @Transactional
    BookLoan saveBookLoan(BookLoan bookLoan);

    List<BookLoan> getBookLoansByISBN(String isbn);

    List<BookLoan> findBookLoansByBorrowerName(String borrower);

    boolean isBookAvailable(String isbn);

    List<BookLoan> getBookLoansByBorrowerID(String cardId);

    List<BookLoan> findByBorrowerAndDateInIsNull(Borrower borrower);

    List<BookLoan> getAllBookLoans();


    Integer countActiveLoansByBorrower(Borrower borrower);
}
