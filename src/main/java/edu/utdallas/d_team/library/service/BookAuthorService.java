package edu.utdallas.d_team.library.service;

import edu.utdallas.d_team.library.entity.BookAuthor;
import edu.utdallas.d_team.library.entity.BookAuthorId;

import java.util.List;


public interface BookAuthorService {
    BookAuthor saveBookAuthorAssociation(BookAuthor bookAuthor);

    void deleteBookAuthorAssociation(BookAuthorId bookAuthorId);

    List<BookAuthor> findBookAuthorsByAuthorId(Integer authorId);

    List<BookAuthor> findBookAuthorsByAuthorName(String name);

    List<BookAuthor> findBookAuthorsByBookIsbn(String isbn);

    List<BookAuthor> findBookAuthorsByBookTitle(String title);

    boolean existsById(BookAuthorId bookAuthorId);

    List<BookAuthor> findByIsbnTitleAuthorContaining(String query);
}
