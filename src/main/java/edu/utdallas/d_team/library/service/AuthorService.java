package edu.utdallas.d_team.library.service;

import edu.utdallas.d_team.library.entity.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    Author saveAuthor(Author author);
    Optional<Author> findAuthorById(Integer id);
    List<Author> findAllAuthors();
    void deleteAuthorById(Integer id);
    Optional<Author> findAuthorByName(String name);
    List<Author> findAuthorsByNameContaining(String name);
}
