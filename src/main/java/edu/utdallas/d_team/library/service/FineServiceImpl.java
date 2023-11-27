package edu.utdallas.d_team.library.service;

import edu.utdallas.d_team.library.entity.Fine;
import edu.utdallas.d_team.library.repository.FineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FineServiceImpl implements FineService {
    private final FineRepository fineRepository;

    @Autowired
    public FineServiceImpl(FineRepository fineRepository){
        this.fineRepository = fineRepository;
    }

    @Override
    public Fine saveFine(Fine fine) {
        return fineRepository.save(fine);
    }

    public Optional<Fine> findFineByLoanID(Integer id) {
        return fineRepository.findById(id);
    }

    public List<Fine> findUnpaidFinesByBorrowerId(String borrowerId) {
        return fineRepository.findByBookLoan_Borrower_CardIdAndPaid(borrowerId, false);
    }


}
