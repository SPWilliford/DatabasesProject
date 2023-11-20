package edu.utdallas.d_team.library.service;

import edu.utdallas.d_team.library.entity.Author;
import edu.utdallas.d_team.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class AuthorServiceImpl implements AuthorService {

    private  final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository){

        this.authorRepository = authorRepository;
    }
    @Override
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Optional<Author> findAuthorById(Integer id) {
        return authorRepository.findById(id);
    }

    @Override
    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public void deleteAuthorById(Integer id) {
        authorRepository.deleteById(id);
    }

    @Override
    public Optional<Author> findAuthorByName(String name) {
        return authorRepository.findByName(name);
    }

    @Override
    public List<Author> findAuthorsByNameContaining(String name) {
        return authorRepository.findByNameContaining(name);
    }

}
