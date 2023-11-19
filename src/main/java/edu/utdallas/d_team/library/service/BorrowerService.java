package edu.utdallas.d_team.library.service;

import edu.utdallas.d_team.library.entity.Borrower;

public interface BorrowerService {
    Borrower saveBorrower(Borrower borrower);
    boolean existsBySsn(String ssn);
}
