package edu.utdallas.d_team.library.repository;

import edu.utdallas.d_team.library.entity.BookAuthor;
import edu.utdallas.d_team.library.entity.BookAuthorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookAuthorRepository extends JpaRepository<BookAuthor, BookAuthorId> {
    @Query("select b from BookAuthor b where b.author.id = ?1")
    List<BookAuthor> findByAuthorId(Integer id);

    @Query("select b from BookAuthor b where b.author.name = ?1")
    List<BookAuthor> findByAuthorName(String name);

    @Query("select b from BookAuthor b where b.isbn.isbn = ?1")
    List<BookAuthor> findByBookIsbn(String isbn);

    @Query("select b from BookAuthor b where b.isbn.title = ?1")
    List<BookAuthor> findByBookTitle(String title);

    @Query("SELECT ba FROM BookAuthor ba " +
            "JOIN ba.isbn b " +
            "JOIN ba.author a " +
            "WHERE LOWER(b.isbn) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
            "OR LOWER(b.title) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
            "OR LOWER(a.name) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    List<BookAuthor> findByIsbnTitleAuthorContaining(@Param("searchText") String searchText);


}