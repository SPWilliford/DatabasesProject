package edu.utdallas.d_team.library.repository;

import edu.utdallas.d_team.library.entity.BookLoan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookLoanRepository extends JpaRepository<BookLoan, Integer> {

    List<BookLoan> findByBook_IsbnAndDateInIsNull(String isbn);
}