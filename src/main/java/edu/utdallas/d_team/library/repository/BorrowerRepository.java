package edu.utdallas.d_team.library.repository;

import edu.utdallas.d_team.library.entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowerRepository extends JpaRepository<Borrower, Integer> {
}