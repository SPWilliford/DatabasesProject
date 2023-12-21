package edu.utdallas.d_team.library.gui;

import edu.utdallas.d_team.library.entity.Borrower;
import edu.utdallas.d_team.library.entity.Fine;
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
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Separator;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.List;

@Component
public class FinesTab {

    
    private final LibraryGuiMediator mediator;
    private Borrower selectedBorrower = null;
    private Fine selectedFine = null;

    @Autowired
    public FinesTab(LibraryGuiMediator mediator) {
        this.mediator = mediator;
    }


    public Node getContent() {
        // the main view for fines tab content.
        // Left side has a button that updates fines and then lists all currently unpaid fines in a list view
        // right side is a borrower search, list of current fines, and button to pay for selected fines.
        HBox mainLayout = new HBox(10);
        mainLayout.setAlignment(Pos.CENTER);
        HBox.setMargin(mainLayout, new Insets(100, 30, 0, 30));
        Separator separator = new Separator(Orientation.VERTICAL);
        Node leftSide = getLeftSide();
        Node rightSide = getRightSide();
        mainLayout.getChildren().addAll(leftSide, separator, rightSide);
        return mainLayout;
    }

    private Node getLeftSide() {
        //format viewing of all fines in system
        VBox leftSide = new VBox(10);
        HBox.setMargin(leftSide, new Insets(60, 30, 0, 30));
        Text allFines = new Text("View all fines:");
        allFines.setFont(Font.font(20));
        Button updateFinesButton = new Button("Update Fines");
        ListView<Fine> finesListView = new ListView<>();
        
        updateFinesButton.setOnAction(actionEvent -> calculateFines(finesListView));
        leftSide.getChildren().addAll(allFines, finesListView, updateFinesButton);
        leftSide.setAlignment(Pos.TOP_LEFT);
        return leftSide;
    }

    private void calculateFines(ListView<Fine> finesListView) {
        List<Fine> unresolvedFines = mediator.calculateFines();
        finesListView.setItems(FXCollections.observableArrayList(unresolvedFines));
    }

    private Node getRightSide() {

        // rightSide contains 2 VBoxes, side by side
        HBox rightSide = new HBox(10);
        HBox.setMargin(rightSide, new Insets(60, 30, 0, 30));
       
        // first has borrower search field and button and info
        VBox borrowerSearchAndInfo = new VBox(10);
        Text process = new Text("Process Fines\n\n");
        process.setFont(Font.font(22));
        Text text1 = new Text("Lookup Borrower:");
        text1.setFont(Font.font(13));
        TextField borrowerSearchField = new TextField();
        Button borrowerSearchButton = new Button("Enter");
        borrowerSearchField.setPromptText("Enter Borrower ID");

        TextArea borrowerInformation = new TextArea();
        borrowerSearchAndInfo.getChildren().addAll(process, text1, borrowerSearchField, borrowerSearchButton, borrowerInformation);

        // second has fines list and button to allow payment
        VBox finesAndPayment = new VBox(10);
        HBox.setMargin(finesAndPayment, new Insets(0, 30, 0, 30));
        Button payFineButton = new Button("Pay Fine");
        Text fines = new Text("Borrower's Fines:");
        ListView<Fine> finesListView = new ListView<>();
        finesAndPayment.getChildren().addAll(fines,finesListView, payFineButton);
        finesAndPayment.setAlignment(Pos.TOP_LEFT);

        borrowerSearchButton.setOnAction(actionEvent -> {
            handleBorrowerSearch(borrowerSearchField, borrowerInformation, finesListView);
        });

        payFineButton.setOnAction(actionEvent -> {
            handleFinePayment(finesListView);
        });



        rightSide.getChildren().addAll(borrowerSearchAndInfo, finesAndPayment);
        return rightSide;
    }

    private void handleFinePayment(ListView<Fine> finesListView) {
        selectedFine = finesListView.getSelectionModel().getSelectedItem();
        if (selectedFine != null) {
            selectedFine.setPaid(true);
            mediator.saveFine(selectedFine);
        }
    }

    private void handleBorrowerSearch(TextField borrowerSearchField, TextArea borrowerInformation, ListView<Fine> fineListView) {
        String borrowerId = borrowerSearchField.getText().trim();
        this.selectedBorrower = mediator.getBorrowerInfo(borrowerId);
        if (selectedBorrower != null) {
            // Display borrower information
            String borrowerInfo = String.format("Name: %s\nAddress: %s\nPhone: %s",
                    selectedBorrower.getBname(),
                    selectedBorrower.getAddress(),
                    selectedBorrower.getPhone());
            borrowerInformation.setText(borrowerInfo);

            // list all the unpaid fines for selected borrower
            List<Fine> unpaidFines = mediator.findUnpaidFinesByBorrowerId(borrowerId);
            fineListView.setItems(FXCollections.observableArrayList(unpaidFines));
        }
    }

}
