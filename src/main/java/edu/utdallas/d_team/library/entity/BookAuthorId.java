package edu.utdallas.d_team.library.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BookAuthorId implements Serializable {

    @Serial
    private static final long serialVersionUID = -6185617016418863727L;
    @Column(name = "Author_id", nullable = false)
    private Integer authorId;

    @Column(name = "Isbn", nullable = false, length = 10)
    private String isbn;

    public BookAuthorId(Integer authorId, String isbn) {
        this.authorId = authorId;
        this.isbn = isbn;
    }

    public BookAuthorId() {

    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BookAuthorId entity = (BookAuthorId) o;
        return Objects.equals(this.isbn, entity.isbn) &&
                Objects.equals(this.authorId, entity.authorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, authorId);
    }

}