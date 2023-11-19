package edu.utdallas.d_team.library;

import edu.utdallas.d_team.library.fileparser.BooksFileParser;
import edu.utdallas.d_team.library.fileparser.BorrowersFileParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LibraryApplication {
    @Value("${app.parse-files-on-startup}")
    private boolean parseFilesOnStartup;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(LibraryApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(BooksFileParser booksFileParser, BorrowersFileParser borrowersFileParser) {
        return args -> {
            if (parseFilesOnStartup) {
                booksFileParser.parseBookFile("books.csv");
                borrowersFileParser.parseBorrowersFile("borrowers.csv");
            }
        };
    }
}
