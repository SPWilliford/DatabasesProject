package edu.utdallas.d_team.library.repository;

import edu.utdallas.d_team.library.entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BorrowerRepository extends JpaRepository<Borrower, Integer> {
    boolean existsBySsn(String ssn);

    Borrower findByCardId(String cardId);

}