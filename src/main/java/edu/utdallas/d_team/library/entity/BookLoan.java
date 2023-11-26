package edu.utdallas.d_team.library.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "BOOK_LOANS")
public class BookLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Loan_id")
    private Integer loanId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Isbn", referencedColumnName = "Isbn", nullable = false)
    private Book book;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Card_id", referencedColumnName = "Card_id", nullable = false)
    private Borrower borrower;

    @Column(name = "Date_out", nullable = false)
    private LocalDate dateOut;

    @Column(name = "Due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "Date_in")
    private LocalDate dateIn;

    // Constructors, getters and setters

    public BookLoan() {}

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Borrower getBorrower() {
        return borrower;
    }

    public void setBorrower(Borrower borrower) {
        this.borrower = borrower;
    }

    public LocalDate getDateOut() {
        return dateOut;
    }

    public void setDateOut(LocalDate dateOut) {
        this.dateOut = dateOut;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getDateIn() {
        return dateIn;
    }

    public void setDateIn(LocalDate dateIn) {
        this.dateIn = dateIn;
    }

    @Override
    public String toString() {
        return  "Loan ID: " + loanId + ", " + book +
                ", Date Out: " + dateOut +
                ", Date Due: " + dueDate;

    }
}