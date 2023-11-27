package edu.utdallas.d_team.library.repository;

import edu.utdallas.d_team.library.entity.Fine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FineRepository extends JpaRepository<Fine, Integer> {
    List<Fine> findByBookLoan_Borrower_CardIdAndPaid(String borrowerId, boolean paid);
}