package edu.utdallas.d_team.library.integration;

import edu.utdallas.d_team.library.entity.Author;
import edu.utdallas.d_team.library.entity.Book;
import edu.utdallas.d_team.library.entity.BookAuthor;
import edu.utdallas.d_team.library.entity.Borrower;
import edu.utdallas.d_team.library.entity.BookLoan;
import edu.utdallas.d_team.library.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

import java.util.stream.Collectors;

@Component
public class LibraryGuiMediator {
    private final BookService bookService;
    private final AuthorService authorService;
    private final BookAuthorService bookAuthorService;

    private final BookLoanService bookLoanService;

    private final BorrowerService borrowerService;

    @Autowired
    public LibraryGuiMediator(BookService bookService, AuthorService authorService, BookAuthorService bookAuthorService, BorrowerService borrowerService, BookLoanService bookLoanService){
        this.bookService = bookService;
        this.authorService = authorService;
        this.bookAuthorService = bookAuthorService;
        this.borrowerService = borrowerService;
        this.bookLoanService = bookLoanService;
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


    public Optional<Book> findBookByIsbn(String isbn) {
        return bookService.findBookByIsbn(isbn);
    }
}
