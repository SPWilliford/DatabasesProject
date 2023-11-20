package edu.utdallas.d_team.library;

import edu.utdallas.d_team.library.fileparser.BooksFileParser;
import edu.utdallas.d_team.library.fileparser.BorrowersFileParser;
import edu.utdallas.d_team.library.gui.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

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
            MainView mainView = new MainView();
            Scene scene = new Scene(mainView, 800, 600);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Library Application");
            primaryStage.show();
        }
        @Override
        public void stop() {
            LibraryApplication.springContext.close();
        }
    }
}
