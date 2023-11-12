package edu.utdallas.d_team.library.repository;

import edu.utdallas.d_team.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {
    Book findByTitle(String title);

    List<Book> findByTitleContaining(String title);

}