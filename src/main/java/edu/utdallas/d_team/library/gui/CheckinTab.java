package edu.utdallas.d_team.library.gui;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CheckinTab {
    public Node getContent() {

        TextField bookLoanSearch = new TextField();
        Button bookLoanSearchButton = new Button("Search");
        ListView<String> searchResults = new ListView<>();
        return new VBox(bookLoanSearch, bookLoanSearchButton, searchResults);
    }
}
