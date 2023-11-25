package edu.utdallas.d_team.library.service;

import edu.utdallas.d_team.library.entity.BookLoan;
import org.springframework.transaction.annotation.Transactional;

public interface BookLoanService {

    @Transactional
    BookLoan saveBookLoan(BookLoan bookLoan);
    boolean isBookAvailable(String isbn);


}
