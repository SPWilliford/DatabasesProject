package edu.utdallas.d_team.library.service;

import edu.utdallas.d_team.library.entity.BookLoan;
import edu.utdallas.d_team.library.repository.BookLoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookLoanServiceImpl implements BookLoanService{

    private final BookLoanRepository bookLoanRepository;
    @Autowired
    public BookLoanServiceImpl(BookLoanRepository bookLoanRepository) {
        this.bookLoanRepository = bookLoanRepository;
    }
    @Override
    public BookLoan saveBookLoan(BookLoan bookLoan) {
        return bookLoanRepository.save(bookLoan);
    }
}
