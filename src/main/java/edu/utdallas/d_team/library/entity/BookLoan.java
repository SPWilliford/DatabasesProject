package edu.utdallas.d_team.library.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "book_loans")
public class BookLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Loan_id", nullable = false)
    private Integer id;

    @Column(name = "Date_out", nullable = false)
    private LocalDate dateOut;

    @Column(name = "Due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "Date_in")
    private LocalDate dateIn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

}