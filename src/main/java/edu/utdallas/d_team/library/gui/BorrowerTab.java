package edu.utdallas.d_team.library.gui;

import edu.utdallas.d_team.library.integration.LibraryGuiMediator;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class BorrowerTab {

    private final LibraryGuiMediator mediator;


    @Autowired
    public BorrowerTab(LibraryGuiMediator mediator) {
        this.mediator = mediator;
    }


    public Node getContent() {

        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        TextField ssnField = new TextField();
        ssnField.setPromptText("Social Security Number: XXX-XX-XXXX");

        TextField addressField = new TextField();
        addressField.setPromptText("Address");
        TextField cityField = new TextField();
        cityField.setPromptText("City");
        TextField stateField = new TextField();
        stateField.setPromptText("State");

        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone: XXX-XXX-XXXX");

        Button submitButton = new Button("Create Account");
        Label messageLabel = new Label();

        submitButton.setOnAction(event -> {
            String name = nameField.getText().trim();
            String ssn = ssnField.getText().trim();
            String address = addressField.getText().trim();
            String city = cityField.getText().trim();
            String state = stateField.getText().trim();
            String fullAddress = address + ", " + city + ", " + state;
            String phone = phoneField.getText().trim();

            String message = mediator.createBorrower(name, ssn, fullAddress, phone);
            messageLabel.setText(message);

        });

        VBox layout = new VBox(10, nameField, ssnField, addressField, cityField, stateField, phoneField, submitButton, messageLabel);
        layout.setAlignment(Pos.CENTER);
        return layout;
    }


}
