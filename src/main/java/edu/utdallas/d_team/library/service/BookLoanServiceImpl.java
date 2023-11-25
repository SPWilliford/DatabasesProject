package edu.utdallas.d_team.library.service;

import edu.utdallas.d_team.library.entity.BookLoan;
import edu.utdallas.d_team.library.repository.BookLoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookLoanServiceImpl implements BookLoanService{

    private final BookLoanRepository bookLoanRepository;
    @Autowired
    public BookLoanServiceImpl(BookLoanRepository bookLoanRepository) {
        this.bookLoanRepository = bookLoanRepository;
    }
    @Override
    @Transactional
    public BookLoan saveBookLoan(BookLoan bookLoan) {
        return bookLoanRepository.save(bookLoan);
    }

    @Override
    public boolean isBookAvailable(String isbn) {
        List<BookLoan> activeLoans = bookLoanRepository.findByBook_IsbnAndDateInIsNull(isbn);
        return activeLoans.isEmpty();
    }


}
