package edu.utdallas.d_team.library.gui;

import edu.utdallas.d_team.library.entity.BookLoan;
import edu.utdallas.d_team.library.entity.Borrower;
import edu.utdallas.d_team.library.integration.LibraryGuiMediator;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class CheckinTab {
    private final LibraryGuiMediator mediator;
    private final TextArea messageArea = new TextArea();
    private final ListView<BookLoan> currentLoansView = new ListView<>();
    private List<BookLoan> bookLoansList = new ArrayList<>();
    private Borrower selectedBorrower = null;


    @Autowired
    public CheckinTab(LibraryGuiMediator mediator) {
        this.mediator = mediator;
    }


    public Node getContent() {

    Node borrowerSearchSection = createBorrowerSearchSection();


    return new HBox(borrowerSearchSection, messageArea);
    }

    private Node createBorrowerSearchSection() {
        TextField borrowerIdField = new TextField();
        borrowerIdField.setPromptText("Enter Borrower ID");
        Button lookupButton = new Button("Lookup Borrower");
        TextArea borrowerInfoDisplay = new TextArea();
        Button checkinButton = new Button("Check-In");


        lookupButton.setOnAction(actionEvent -> {
            String borrowerId = borrowerIdField.getText().trim();
            this.selectedBorrower = mediator.getBorrowerInfo(borrowerId);
            if (selectedBorrower != null) {
                // Display borrower information
                String borrowerInfo = String.format("Name: %s\nAddress: %s\nPhone: %s",
                        selectedBorrower.getBname(),
                        selectedBorrower.getAddress(),
                        selectedBorrower.getPhone());
                borrowerInfoDisplay.setText(borrowerInfo);

                // also need to get the current loans for the borrower and list them
                bookLoansList = mediator.getBookLoansByBorrower(selectedBorrower);
                currentLoansView.setItems(FXCollections.observableArrayList(bookLoansList));

            } else {
                borrowerInfoDisplay.setText("Borrower not found for ID: " + borrowerId);
            }
        });

        checkinButton.setOnAction(event -> {
            BookLoan selectedLoan = currentLoansView.getSelectionModel().getSelectedItem();
            if (selectedLoan != null) {
                // Set the return date to now
                selectedLoan.setDateIn(LocalDate.now());

                // Call mediator to save the updated loan
                String message = mediator.checkInBook(selectedLoan);
                // Display a confirmation message or an error message based on the operation's result
                // You can use an alert or a label for this purpose
                messageArea.setText(message);
            } else {
                // Display an error message if no loan is selected
                messageArea.setText("No book loan selected for check-in.");
            }
        });

        return new VBox(borrowerIdField, lookupButton, borrowerInfoDisplay, currentLoansView, checkinButton);
    }




}


