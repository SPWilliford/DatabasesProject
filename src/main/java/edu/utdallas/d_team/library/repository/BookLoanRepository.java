package edu.utdallas.d_team.library.repository;

import edu.utdallas.d_team.library.entity.BookLoan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookLoanRepository extends JpaRepository<BookLoan, Integer> {
}