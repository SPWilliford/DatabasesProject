package edu.utdallas.d_team.library.gui;

import edu.utdallas.d_team.library.entity.BookLoan;
import edu.utdallas.d_team.library.entity.Borrower;
import edu.utdallas.d_team.library.integration.LibraryGuiMediator;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
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
    
    //format message area
    HBox message = new HBox(messageArea);
    Text checkedIn = new Text("Checked-In:");
    VBox messages = new VBox(5, checkedIn, message);
    HBox.setMargin(messages, new Insets(150, 0, 150 ,30));
    messages.setAlignment(Pos.TOP_LEFT); 
    VBox.setVgrow(message, Priority.ALWAYS);
    
    HBox checkInPage = new HBox(borrowerSearchSection, messages);
    checkInPage.setAlignment(Pos.TOP_CENTER); 
    

    return checkInPage;
    }

    private Node createBorrowerSearchSection() {
        TextField searchField = new TextField();
        searchField.setPromptText("Enter Borrower ID, Book ISBN or Borrower Name");
        Button lookupButton = new Button("Search");
        TextArea borrowerInfoDisplay = new TextArea();
        Button checkinButton = new Button("Check-In");
        Text title = new Text("Library Check-In");
        Text booksOut = new Text("Books checked out:");
        title.setFont(Font.font(20));


        lookupButton.setOnAction(actionEvent -> {
            String searchText = searchField.getText().trim();
            List<BookLoan> results = mediator.searchBookLoans(searchText);
            currentLoansView.setItems(FXCollections.observableArrayList(results));
        });

        currentLoansView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedBorrower = newSelection.getBorrower();
                String borrowerInfo = String.format("Name: %s\nAddress: %s\nPhone: %s",
                        selectedBorrower.getBname(),
                        selectedBorrower.getAddress(),
                        selectedBorrower.getPhone());
                borrowerInfoDisplay.setText(borrowerInfo);
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


       VBox bSearchSection = new VBox(5, title, borrowerIdField, lookupButton, borrowerInfoDisplay, booksOut, currentLoansView, checkinButton);
       HBox.setMargin(bSearchSection, new Insets(40, 0, 50 ,30));


       return bSearchSection; 
    }

}
