package edu.utdallas.d_team.library.gui;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import javafx.scene.layout.VBox;

import java.awt.*;

public class SearchTab {
    public Node getContent() {
        TextField searchField = new TextField();
        Button searchButton = new Button("Search");
        ListView<String> searchResults = new ListView<>();

        // Add event handlers and logic.
        return new VBox(10, searchField, searchButton, searchResults);
    }
}
