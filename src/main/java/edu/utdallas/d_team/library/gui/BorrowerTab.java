package edu.utdallas.d_team.library.gui;

import edu.utdallas.d_team.library.integration.LibraryGuiMediator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
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

        Text create = new Text("Create New Borrower:\n");
        
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

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(400, 200); 
        //Setting the padding  
        gridPane.setPadding(new Insets(20, 100, 1, 100)); 
      
      //Setting the vertical and horizontal gaps between the columns 
        gridPane.setVgap(5); 
        gridPane.setHgap(5);       
      
      //Setting the Grid alignment 
        gridPane.setAlignment(Pos.CENTER); 
       
      //Arranging all the nodes in the grid 
        gridPane.add(create, 1, 0); 

        gridPane.add(nameField, 1, 1,2,1); 
        gridPane.add(ssnField, 1, 2, 2, 1);  
        gridPane.add(addressField, 1, 3,2,1); 
        gridPane.add(cityField, 1, 4, 2, 1); 
        gridPane.add(stateField, 1, 5, 2, 1); 
        gridPane.add(phoneField, 1, 6, 2, 1); 
        gridPane.add(submitButton, 2, 7); 
        gridPane.add(messageLabel, 1, 8, 3, 3); 
         
        create.setFont(Font.font(30));

    
        return gridPane;
    }


}
