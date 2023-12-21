package edu.utdallas.d_team.library.gui;

import edu.utdallas.d_team.library.integration.LibraryGuiMediator;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
        Text text1 = new Text("Search for a book:");
        TextField searchField = new TextField();
        Text resultList = new Text("Results:");
        searchField.setPromptText("Search by title, author, and/or ISBN");
        Button searchButton = new Button("Enter");
        ListView<String> searchResults = new ListView<>();
        

        searchButton.setOnAction(event -> {
            String query = searchField.getText();
            List<String> results = mediator.searchBooks(query);
            searchResults.setItems(FXCollections.observableArrayList(results));
        });

        //page is formatted as 2 VBoxes inside an HBox
      Text title = new Text("\nLibrary Catalog Search\n\n\n");
      title.setFont(Font.font(35));

        //format search button
      VBox sButton = new VBox(searchButton);
      sButton.setAlignment(Pos.TOP_RIGHT); 

      VBox searchArea = new VBox(5, title, text1, searchField, sButton);
      HBox.setMargin(searchArea, new Insets(40, 0, 30 ,200));

        //format search and results
      VBox resultArea = new VBox(5, resultList, searchResults);
      HBox.setMargin(resultArea, new Insets(40, 50, 30 ,0));
      HBox.setHgrow(searchArea, Priority.ALWAYS);
      HBox.setHgrow(resultArea, Priority.ALWAYS);

      HBox searchPage = new HBox(10, searchArea, resultArea);
      HBox.setMargin(searchPage, new Insets(40, 0, 30 ,0));
      searchPage.setAlignment(Pos.TOP_CENTER); 

     return searchPage;
    }
}
