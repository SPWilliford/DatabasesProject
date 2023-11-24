package edu.utdallas.d_team.library.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "FINES")
public class Fine {
    @Id
    @Column(name = "Loan_id", nullable = false)
    private Integer loanId;

    @Column(name = "Fine_amt", nullable = false, precision = 6, scale = 2)
    private BigDecimal fineAmt;

    @Column(name = "Paid", nullable = false)
    private Boolean paid;

    @OneToOne
    @JoinColumn(name = "Loan_id", referencedColumnName = "Loan_id", insertable = false, updatable = false)
    private BookLoan bookLoan;

    // Constructors, getters, and setters

    public Fine() {}

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public BigDecimal getFineAmt() {
        return fineAmt;
    }

    public void setFineAmt(BigDecimal fineAmt) {
        this.fineAmt = fineAmt;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public BookLoan getBookLoan() {
        return bookLoan;
    }

    public void setBookLoan(BookLoan bookLoan) {
        this.bookLoan = bookLoan;
    }
}