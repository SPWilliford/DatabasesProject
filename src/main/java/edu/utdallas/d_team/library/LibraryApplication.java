package edu.utdallas.d_team.library;

import edu.utdallas.d_team.library.fileparser.BooksFileParser;
import edu.utdallas.d_team.library.fileparser.BorrowersFileParser;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.awt.*;

@SpringBootApplication
public class LibraryApplication {

    private static ConfigurableApplicationContext springContext;
    private Stage primaryStage;

    public static void main(String[] args) {
        Application.launch(JavaFXApplication.class, args);
    }

    @Value("${app.parse-files-on-startup}")
    private boolean parseFilesOnStartup;

    @Bean
    public CommandLineRunner run(BooksFileParser booksFileParser, BorrowersFileParser borrowersFileParser) {
        return args -> {
            if (parseFilesOnStartup) {
                booksFileParser.parseBookFile("books.csv");
                borrowersFileParser.parseBorrowersFile("borrowers.csv");
            }
        };
    }

    public static class JavaFXApplication extends Application {

        @Override
        public void init() {
            String[] args = getParameters().getRaw().toArray(new String[0]);
            LibraryApplication.springContext = SpringApplication.run(LibraryApplication.class, args);
        }

        @Override
        public void start(Stage primaryStage) throws Exception {
            // Create components
            TextField searchField = new TextField();
            searchField.setPromptText("Enter ISBN, title, or author...");

            Button searchButton = new Button("Search");

            // Action on clicking search button
            searchButton.setOnAction(event -> {
                String searchText = searchField.getText();
                // Here you can call a method to perform the search using searchText
                // For example: performSearch(searchText);
            });

            VBox root = new VBox(10); // 10 is the spacing between elements
            root.getChildren().addAll(searchField, searchButton);

            Scene scene = new Scene(root, 400, 200); // width = 400, height = 200

            primaryStage.setTitle("Book Search");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        @Override
        public void stop() {
            LibraryApplication.springContext.close();
        }
    }
}
