package edu.utdallas.d_team.library.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "fines")
public class Fine {
    @Id
    @Column(name = "Loan_id", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Loan_id", nullable = false)
    private BookLoan bookLoans;

    @Column(name = "Fine_amt", nullable = false, precision = 4, scale = 2)
    private BigDecimal fineAmt;

    @Column(name = "Paid", nullable = false)
    private Boolean paid = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BookLoan getBookLoans() {
        return bookLoans;
    }

    public void setBookLoans(BookLoan bookLoans) {
        this.bookLoans = bookLoans;
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

}