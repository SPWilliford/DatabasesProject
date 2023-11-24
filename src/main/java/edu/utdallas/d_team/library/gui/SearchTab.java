package edu.utdallas.d_team.library.gui;

import edu.utdallas.d_team.library.integration.LibraryGuiMediator;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SearchTab {

    private LibraryGuiMediator mediator;
    @Autowired
    public SearchTab(LibraryGuiMediator mediator) {
        this.mediator = mediator;
    }

    public Node getContent() {
        TextField searchField = new TextField();
        Button searchButton = new Button("Search");
        ListView<String> searchResults = new ListView<>();

        searchButton.setOnAction(event -> {
            String query = searchField.getText();
            List<String> results = mediator.searchBooks(query);
            searchResults.setItems(FXCollections.observableArrayList(results));
        });

        // Add event handlers and logic.
        return new VBox(10, searchField, searchButton, searchResults);
    }
}
