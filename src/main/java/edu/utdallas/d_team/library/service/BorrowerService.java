package edu.utdallas.d_team.library.service;

import edu.utdallas.d_team.library.entity.Borrower;

import java.util.Optional;

public interface BorrowerService {
    Borrower saveBorrower(Borrower borrower);
    boolean existsBySsn(String ssn);

    public Optional<Borrower> findBorrowerByCardId(String cardId);

}
