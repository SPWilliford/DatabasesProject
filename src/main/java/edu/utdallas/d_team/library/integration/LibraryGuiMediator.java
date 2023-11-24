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

    private String formatBookWithAuthors(Book book, Set<Author> authors) {
        String authorsString = authors.stream()
                .map(Author::getName)
                .collect(Collectors.joining(", "));
        return "ISBN: " + book.getIsbn() + ", Title: " + book.getTitle() + ", Authors: " + authorsString;
    }

    public Borrower getBorrowerInfo(String cardId) {
        // Assuming you have a method in BorrowerService to find a borrower by Card_id
        return borrowerService.findBorrowerByCardId(cardId).orElse(null);
    }

    public String createBookLoan(List<String> isbns, String cardId) {
        try {
            Optional<Borrower> borrowerOpt = borrowerService.findBorrowerByCardId(cardId);

            if (borrowerOpt.isEmpty()) {
                return "Error: Borrower not found with Card ID: " + cardId;
            }
            Borrower borrower = borrowerOpt.get();

            for (String isbn : isbns) {
                Optional<Book> bookOpt = bookService.findBookByIsbn(isbn);

                if (bookOpt.isEmpty()) {
                    return "Error: Book not found with ISBN: " + isbn;
                }
                Book book = bookOpt.get();

                // Additional checks can be performed here, like checking for maximum loans, or if the book is already loaned out.

                BookLoan newLoan = new BookLoan();
                newLoan.setBook(book);
                newLoan.setBorrower(borrower);
                LocalDate currentDate = LocalDate.now();
                newLoan.setDateOut(currentDate);
                newLoan.setDueDate(currentDate.plusDays(14));

                // Save the new BookLoan
                bookLoanService.saveBookLoan(newLoan);
            }
            return "Success: Book(s) checked out.";
        } catch (Exception e) {
            return "Error: " + e.getMessage(); // Simplified error handling
        }
    }


    public Optional<Book> findBookByIsbn(String isbn) {
        return bookService.findBookByIsbn(isbn);
    }
}
