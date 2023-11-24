package edu.utdallas.d_team.library.gui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;


@Component
public class BorrowerTab {
    public Node getContent() {

        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        TextField ssnField = new TextField();
        ssnField.setPromptText("Social Security Number");

        TextField addressField = new TextField();
        addressField.setPromptText("Address");

        Button submitButton = new Button("Create Account");
        Label messageLabel = new Label();

        submitButton.setOnAction(event -> {
            // Logic to handle the creation of a new borrower
            // Validate input, interact with the database, etc.
            // Update messageLabel with success or error message
        });

        VBox layout = new VBox(10, nameField, ssnField, addressField, submitButton, messageLabel);
        layout.setAlignment(Pos.CENTER);
        return layout;
    }
}
