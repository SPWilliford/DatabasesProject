package edu.utdallas.d_team.library.repository;

import edu.utdallas.d_team.library.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Optional<Author> findByName(String name);

    List<Author> findByNameContaining(String name);
}