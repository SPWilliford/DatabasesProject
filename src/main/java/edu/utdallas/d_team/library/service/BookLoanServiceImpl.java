package edu.utdallas.d_team.library.service;

import edu.utdallas.d_team.library.entity.BookLoan;
import edu.utdallas.d_team.library.entity.Borrower;
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

    @Override
    public List<BookLoan> getBookLoansByBorrowerID(String cardId) {
        return bookLoanRepository.findByBorrower_CardId(cardId);
    }

    @Override
    public List<BookLoan> getBookLoansByISBN(String isbn){
        return bookLoanRepository.findByBook_Isbn(isbn);
    }

    @Override
    public List<BookLoan> findBookLoansByBorrowerName(String borrower_name_substring) {
        return bookLoanRepository.findByBorrower_BnameLikeIgnoreCase("%" + borrower_name_substring + "%");
    }


    @Override
    public List<BookLoan> findByBorrowerAndDateInIsNull(Borrower borrower) {
        return bookLoanRepository.findByBorrowerAndDateInIsNull(borrower);
    }

    public List<BookLoan> getBookLoansByBorrower(Borrower borrower) {
        return bookLoanRepository.findByBorrowerAndDateInIsNull(borrower);
    }


    public List<BookLoan> getAllBookLoans() {
        return bookLoanRepository.findAll();
    }

    @Override
    public Integer countActiveLoansByBorrower(Borrower borrower) {

        return bookLoanRepository.countBookLoanByBorrowerAndDateInIsNull(borrower);
    }



}
