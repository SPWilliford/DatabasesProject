package edu.utdallas.d_team.library.repository;

import edu.utdallas.d_team.library.entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BorrowerRepository extends JpaRepository<Borrower, Integer> {
    boolean existsBySsn(String ssn);

    Borrower findByCardId(String cardId);

    @Query("SELECT MAX(b.cardId) FROM Borrower b")
    String findHighestCardId();

}