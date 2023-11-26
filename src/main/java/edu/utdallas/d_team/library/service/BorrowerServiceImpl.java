package edu.utdallas.d_team.library.service;

import edu.utdallas.d_team.library.entity.Borrower;
import edu.utdallas.d_team.library.repository.BorrowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BorrowerServiceImpl implements BorrowerService{

    private final BorrowerRepository borrowerRepository;
    @Autowired
    public BorrowerServiceImpl(BorrowerRepository borrowerRepository) {
        this.borrowerRepository = borrowerRepository;
    }

    @Override
    public Borrower saveBorrower(Borrower borrower) {
        return borrowerRepository.save(borrower);
    }

    @Override
    public boolean existsBySsn(String ssn) {

        return borrowerRepository.existsBySsn(ssn);
    }

    @Override
    public Optional<Borrower> findBorrowerByCardId(String cardId) {

        return Optional.ofNullable(borrowerRepository.findByCardId(cardId));

    }

    @Override
    public String findHighestCardID() {

        return borrowerRepository.findHighestCardId();
    }

}
