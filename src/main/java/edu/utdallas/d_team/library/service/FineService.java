package edu.utdallas.d_team.library.service;

import edu.utdallas.d_team.library.entity.Fine;

import java.util.List;
import java.util.Optional;

public interface FineService {
    Fine saveFine(Fine fine);

    public Optional<Fine> findFineByLoanID(Integer id);
    public List<Fine> findUnpaidFinesByBorrowerId(String borrowerId);
}
