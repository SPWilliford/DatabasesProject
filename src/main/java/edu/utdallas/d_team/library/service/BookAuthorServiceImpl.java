package edu.utdallas.d_team.library.service;

import edu.utdallas.d_team.library.entity.BookAuthor;
import edu.utdallas.d_team.library.entity.BookAuthorId;
import edu.utdallas.d_team.library.repository.BookAuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class BookAuthorServiceImpl implements BookAuthorService {

    private final BookAuthorRepository bookAuthorRepository;

    @Autowired
    public BookAuthorServiceImpl(BookAuthorRepository bookAuthorRepository) {
        this.bookAuthorRepository = bookAuthorRepository;
    }

    @Override
    @Transactional
    public BookAuthor saveBookAuthorAssociation(BookAuthor bookAuthor) {
        return bookAuthorRepository.save(bookAuthor);
    }

    @Override
    @Transactional
    public void deleteBookAuthorAssociation(BookAuthorId bookAuthorId) {
        bookAuthorRepository.deleteById(bookAuthorId);
    }

    @Override
    public List<BookAuthor> findBookAuthorsByAuthorId(Integer authorId) {
        return bookAuthorRepository.findByAuthorId(authorId);
    }

    @Override
    public List<BookAuthor> findBookAuthorsByAuthorName(String name) {
        return bookAuthorRepository.findByAuthorName(name);
    }


    @Override
    public List<BookAuthor> findBookAuthorsByBookIsbn(String isbn) {

        return bookAuthorRepository.findByBookIsbn(isbn);
    }

    @Override
    public List<BookAuthor> findBookAuthorsByBookTitle(String title) {
        return bookAuthorRepository.findByBookTitle(title);
    }

    @Override
    public boolean existsById(BookAuthorId bookAuthorId) {
        return bookAuthorRepository.existsById(bookAuthorId);
    }

    @Override
    public List<BookAuthor> findByIsbnTitleAuthorContaining(String searchText) {
        return bookAuthorRepository.findByIsbnTitleAuthorContaining(searchText);
    }

}

