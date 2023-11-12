package edu.utdallas.d_team.library.service;

import edu.utdallas.d_team.library.entity.Book;
import java.util.List;
import java.util.Optional;

public interface BookService {
    Book saveBook(Book book);
    Optional<Book> findBookByIsbn(String isbn);
    List<Book> findAllBooks();
    void deleteBookByIsbn(String isbn);
    Book findBookByTitle(String title);
    List<Book> findBooksByTitleContaining(String title);
}
