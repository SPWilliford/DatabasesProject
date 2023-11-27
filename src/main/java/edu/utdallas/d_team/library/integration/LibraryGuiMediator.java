package edu.utdallas.d_team.library.integration;

import edu.utdallas.d_team.library.entity.*;
import edu.utdallas.d_team.library.repository.BorrowerRepository;
import edu.utdallas.d_team.library.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;

import java.util.stream.Collectors;

@Component
public class LibraryGuiMediator {
    private final BookService bookService;
    private final AuthorService authorService;
    private final BookAuthorService bookAuthorService;
    private final FineService fineService;

    private final BookLoanService bookLoanService;

    private final BorrowerService borrowerService;
    private final BorrowerRepository borrowerRepository;


    @Autowired
    public LibraryGuiMediator(BookService bookService, AuthorService authorService,
                              BookAuthorService bookAuthorService,
                              BorrowerService borrowerService, BookLoanService bookLoanService,
                              BorrowerRepository borrowerRepository, FineService fineService)
    {
        this.bookService = bookService;
        this.authorService = authorService;
        this.bookAuthorService = bookAuthorService;
        this.borrowerService = borrowerService;
        this.bookLoanService = bookLoanService;
        this.borrowerRepository = borrowerRepository;
        this.fineService = fineService;
    }

    // This is the method that is used by the SearchTab class
    public List<String> searchBooks(String searchText) {
        List<BookAuthor> bookAuthors = bookAuthorService.findByIsbnTitleAuthorContaining(searchText);
        Map<Book, Set<Author>> bookToAuthorsMap = new HashMap<>();

        for (BookAuthor bookAuthor : bookAuthors) {
            bookToAuthorsMap.computeIfAbsent(bookAuthor.getIsbn(), k -> new HashSet<>())
                    .add(bookAuthor.getAuthor());
        }
        return bookToAuthorsMap.entrySet().stream()
                .map(entry -> formatBookWithAuthors(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    // Used by the searchBooks function, returns the formatted string of book info with authors and availability
    private String formatBookWithAuthors(Book book, Set<Author> authors) {
        String authorsString = authors.stream()
                .map(Author::getName)
                .collect(Collectors.joining(", "));

        String authorsInfo = ", Author(s): " + authorsString;
        String availability = ", Availability: ";
        if (bookLoanService.isBookAvailable(book.getIsbn())){
            availability += "Book is Currently Available";
        }
        else{
            availability += "Book is Currently Unavailable";
        }

        return book.toString() + authorsInfo + availability;
    }

    public Optional<Book> getBookByIsbn(String isbn) {
        return bookService.findBookByIsbn(isbn);
    }

    public Borrower getBorrowerInfo(String cardId) {
        // Assuming you have a method in BorrowerService to find a borrower by Card_id
        return borrowerService.findBorrowerByCardId(cardId).orElse(null);
    }

    public String createBookLoans(List<Book> books, String borrowerCardId) {
        String checkoutStatus = null;
        try {
            Optional<Borrower> opt_borrower = borrowerService.findBorrowerByCardId(borrowerCardId);
            if (opt_borrower.isPresent()){
                Borrower borrower = opt_borrower.get();
                for (Book book : books) {
                    BookLoan newLoan = new BookLoan();
                    newLoan.setBook(book);
                    newLoan.setBorrower(borrower);
                    LocalDate currentDate = LocalDate.now();
                    newLoan.setDateOut(currentDate);
                    newLoan.setDueDate(currentDate.plusDays(14));
                    bookLoanService.saveBookLoan(newLoan);
                }
            }
            checkoutStatus = "Success: Books checked out.";
        } catch (Exception e) {
            checkoutStatus = "Error: " + e.getMessage();
        }
        return checkoutStatus;

    }

    public List<BookLoan> getBookLoansByBorrower(Borrower borrower){
        return bookLoanService.findByBorrowerAndDateInIsNull(borrower);
    }

    public Optional<Book> findBookByIsbn(String isbn) {
        return bookService.findBookByIsbn(isbn);
    }

    public String checkInBook(BookLoan selectedLoan) {
        try {
            LocalDate today = LocalDate.now();
            selectedLoan.setDateIn(today);
            bookLoanService.saveBookLoan(selectedLoan);
            return "Checked In: " + today + " ," + selectedLoan.getBook().toString();
        }
        catch (Exception e) {
            return "Error Checking In Book: " + selectedLoan.getBook().toString();
        }


    }

    public String createBorrower(String name, String ssn, String fullAddress, String phone) {
        // check if there is already a ssn for that borrower
        if (borrowerService.existsBySsn(ssn))
        {
            return "Account already exists with SSN: " + ssn;
        } else if (name.isEmpty() || ssn.isEmpty() || fullAddress.isEmpty()) {
            return "There must be a value for name, ssn, and address";
        }
        else {
            Borrower b = new Borrower();
            b.setCardId(generateNewCardID());
            b.setSsn(ssn);
            b.setBname(name);
            b.setAddress(fullAddress);
            b.setPhone(phone);
            borrowerRepository.save(b);
            return "Created New Borrower Account: " + b.toString();
        }
    }

    private String generateNewCardID() {
        String highestId = borrowerService.findHighestCardID();
        Integer numericPart = Integer.parseInt(highestId.substring(2));
        numericPart++;

        return String.format("ID%06d", numericPart);

    }

    public List<Fine> calculateFines() {
        // for every BookLoan that is past its due date, we check if there is an associated unpaid Fine to update
        List<BookLoan> allLoans = bookLoanService.getAllBookLoans();
        LocalDate today = LocalDate.now();
        List<Fine> unpaidFines = new ArrayList<>();

        for (BookLoan loan : allLoans) {
            if (isLate(loan, today)) {
                Optional<Fine> optionalFine = fineService.findFineByLoanID(loan.getLoanId());
                if (optionalFine.isPresent()){
                    // this means there is already a fine for this late loan
                    Fine fine = optionalFine.get();
                    if (!fine.getPaid()){
                        // fine has not been paid and we add it to the list to update
                        unpaidFines.add(fine);
                    }
                }
                else {
                    // there is not currently a fine for this late loan so we make a new one and then add it to the list
                    Integer id = loan.getLoanId();
                    LocalDate dueDate = loan.getDueDate();
                    long daysOverdue = ChronoUnit.DAYS.between(dueDate, today);
                    BigDecimal fineAmount = BigDecimal.valueOf(daysOverdue * 0.25);
                    Fine f = new Fine();
                    f.setLoanId(id);
                    f.setFineAmt(fineAmount);
                    f.setPaid(false);
                    fineService.saveFine(f);
                    unpaidFines.add(f);
                }
            }
        }
        return unpaidFines;
    }

    private boolean isLate(BookLoan loan, LocalDate today) {
        boolean isLate = false;
       //if it is not returned and its due date is before today
        if (loan.getDateIn() == null && loan.getDueDate().isBefore(today)) {
            return true;
        }
        // OR it has been returned already but after due date
        else if (loan.getDateIn() != null && loan.getDueDate().isBefore(loan.getDateIn())){
            return true;
        }
        else {
            return false;
        }
    }

    public List<Fine> findUnpaidFinesByBorrowerId(String borrowerId) {
           return fineService.findUnpaidFinesByBorrowerId(borrowerId);
    }


    public void saveFine(Fine selectedFine) {
        fineService.saveFine(selectedFine);
    }
}

