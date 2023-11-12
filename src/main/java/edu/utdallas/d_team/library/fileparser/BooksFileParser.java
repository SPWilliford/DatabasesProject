package edu.utdallas.d_team.library.fileparser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import edu.utdallas.d_team.library.entity.Author;
import edu.utdallas.d_team.library.entity.Book;
import edu.utdallas.d_team.library.entity.BookAuthor;
import edu.utdallas.d_team.library.entity.BookAuthorId;
import edu.utdallas.d_team.library.service.AuthorService;
import edu.utdallas.d_team.library.service.BookAuthorService;
import edu.utdallas.d_team.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
@Component
public class BooksFileParser {

    private static final Logger logger = LoggerFactory.getLogger(BooksFileParser.class);
    private final BookService bookService;
    private final AuthorService authorService;
    private final BookAuthorService bookAuthorService;
    @Autowired
    public BooksFileParser(BookService bookService, AuthorService authorService, BookAuthorService bookAuthorService){
        this.bookService = bookService;
        this.authorService = authorService;
        this.bookAuthorService = bookAuthorService;
    }

    public void parseBookFile(String filePath) {

        ClassPathResource resource = new ClassPathResource(filePath);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {

            List<String[]> data = reader.lines()
                    .skip(1)
                    .map(line -> line.split("\t"))
                    .toList();

            for (String[] record : data) {
                processRecord(record);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void processRecord(String[] record) {

        logger.info("Processing record: {}",(Object) record);

        String isbn = record[1];
        String title = record[2];
        // Retrieve the book by ISBN or create and save a new one if it doesn't exist
        Book book = bookService.findBookByIsbn(isbn)
                .orElseGet(() -> {
                    Book newBook = new Book();
                    newBook.setIsbn(isbn);
                    newBook.setTitle(title);
                    return bookService.saveBook(newBook);
                });

        // Process authors
        String[] authors = record[3].split(",");
        for (String name : authors) {
            Author author = authorService.findAuthorByName(name.trim())
                    .orElseGet(()-> {
                        Author newAuthor = new Author();
                        newAuthor.setName(name.trim());
                        return authorService.saveAuthor(newAuthor);
                    });

            BookAuthorId bookAuthorId = new BookAuthorId(author.getId(), book.getIsbn());
            if (!bookAuthorService.existsById(bookAuthorId)){
                BookAuthor bookAuthor = new BookAuthor();
                bookAuthor.setId(bookAuthorId);
                bookAuthor.setAuthor(author);
                bookAuthor.setIsbn(book);
                bookAuthorService.saveBookAuthorAssociation(bookAuthor);
            }
        }
    }
}
