package edu.utdallas.d_team.library.repository;

import edu.utdallas.d_team.library.entity.BookAuthor;
import edu.utdallas.d_team.library.entity.BookAuthorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookAuthorRepository extends JpaRepository<BookAuthor, BookAuthorId> {
    @Query("select b from BookAuthor b where b.author.id = ?1")
    List<BookAuthor> findByAuthorId(Integer id);

    @Query("select b from BookAuthor b where b.author.name = ?1")
    List<BookAuthor> findByAuthorName(String name);

    @Query("select b from BookAuthor b where b.isbn.isbn = ?1")
    List<BookAuthor> findByBookIsbn(String isbn);

    @Query("select b from BookAuthor b where b.isbn.title = ?1")
    List<BookAuthor> findByBookTitle(String title);


}