package edu.utdallas.d_team.library.gui;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class CheckoutTab {
    public Node getContent(){
        TextField bookSearchField = new TextField();
        Button bookSearchButton = new Button("Search Books");
        ListView<String> bookSearchResults = new ListView<>();
        VBox booksBox = new VBox(10, bookSearchField, bookSearchButton, bookSearchResults);
        TextField borrowerSearchField = new TextField();
        Button borrowerSearchButton = new Button("Search Borrowers");
        ListView<String> borrowerSearchResults = new ListView<>();
        VBox borrowerBox = new VBox(10, borrowerSearchField, borrowerSearchButton, borrowerSearchResults);
        return new HBox(10, booksBox, borrowerBox);
    }
}
