package edu.utdallas.d_team.library.service;

import edu.utdallas.d_team.library.entity.Fine;
import edu.utdallas.d_team.library.repository.FineRepository;
import org.springframework.stereotype.Service;

@Service
public class FineServiceImpl implements FineService {
    private final FineRepository fineRepository;

    public FineServiceImpl(FineRepository fineRepository){
        this.fineRepository = fineRepository;
    }

    @Override
    public Fine saveFine(Fine fine) {
        return fineRepository.save(fine);
    }
}
