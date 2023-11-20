package edu.utdallas.d_team.library.repository;

import edu.utdallas.d_team.library.entity.Fine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FineRepository extends JpaRepository<Fine, Integer> {

}