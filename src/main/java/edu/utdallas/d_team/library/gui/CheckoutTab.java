package edu.utdallas.d_team.library.gui;

import edu.utdallas.d_team.library.entity.Book;
import edu.utdallas.d_team.library.entity.Borrower;
import edu.utdallas.d_team.library.integration.LibraryGuiMediator;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CheckoutTab {

    private final LibraryGuiMediator mediator;
    private final List<Book> selectedBooks = new ArrayList<>();
    private final ListView<Book> selectedBooksView = new ListView<>();
    private Borrower selectedBorrower = null;


    @Autowired
    public CheckoutTab(LibraryGuiMediator mediator) {
        this.mediator = mediator;
    }

    public Node getContent() {


        Node bookSearchSection = createBookSearchSection();
        Node borrowerSearchSection = createBorrowerLookupSection();
        Node checkoutSection = createCheckoutSection();

        HBox topSection = new HBox(10, bookSearchSection, borrowerSearchSection, checkoutSection);
        HBox.setHgrow(bookSearchSection, Priority.ALWAYS);
        HBox.setHgrow(borrowerSearchSection, Priority.ALWAYS);
        HBox.setHgrow(checkoutSection, Priority.ALWAYS);


        return topSection;
    }

    private Node createBookSearchSection() {
        TextField searchField = new TextField();
        Button searchButton = new Button("Search");
        ListView<String> searchResults = new ListView<>();
        Button addButton = new Button("Add");
        Button removeButton = new Button("Remove");


        searchButton.setOnAction(event -> {
            String query = searchField.getText();
            List<String> results = mediator.searchBooks(query);
            searchResults.setItems(FXCollections.observableArrayList(results));
        });

        addButton.setOnAction(event -> {
            String selectedBookInfo = String.valueOf(searchResults.getSelectionModel().getSelectedItem());
            handleAddBookToList(selectedBookInfo);
        });

        removeButton.setOnAction(event -> {
            handleRemoveBookFromList();
        });

        HBox searchResultsWithButton = new HBox(5, searchResults, addButton);
        HBox selectedBooksViewWithButton = new HBox(5, selectedBooksView, removeButton);
        VBox bookSearchLayout = new VBox(10);
        bookSearchLayout.getChildren().addAll(
                searchField, searchButton, searchResultsWithButton, selectedBooksViewWithButton
        );
        bookSearchLayout.setPrefWidth(300);
        return bookSearchLayout;
    }

    private String extractIsbn(String bookInfo) {

        return bookInfo.split(",")[0].replace("ISBN: ", "").trim();
    }

    private boolean selectedBooksContains(Book book) {
        return selectedBooks.stream().anyMatch(b -> b.getIsbn().equals(book.getIsbn()));
    }

    private void handleAddBookToList(String selectedBookInfo) {
        String isbn = extractIsbn(selectedBookInfo);
        Optional<Book> bookOpt = mediator.getBookByIsbn(isbn);
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            if (!selectedBooksContains(book)) {
                selectedBooks.add(book);
                selectedBooksView.setItems(FXCollections.observableArrayList(selectedBooks));
            }
        }
    }

    private void handleRemoveBookFromList() {
        Book selectedBook = selectedBooksView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            selectedBooks.remove(selectedBook);
            selectedBooksView.setItems(FXCollections.observableArrayList(selectedBooks));
        }
    }

    private Node createBorrowerLookupSection() {

        TextField borrowerIdField = new TextField();
        borrowerIdField.setPromptText("Enter Borrower ID");

        Button lookupButton = new Button("Lookup Borrower");
        TextArea borrowerInfoDisplay = new TextArea();
        borrowerInfoDisplay.setEditable(false);
        lookupButton.setOnAction(event -> {
            String borrowerId = borrowerIdField.getText().trim();
            this.selectedBorrower = mediator.getBorrowerInfo(borrowerId);
            if (selectedBorrower != null) {
                // Display borrower information
                String borrowerInfo = String.format("Name: %s\nAddress: %s\nPhone: %s",
                        selectedBorrower.getBname(),
                        selectedBorrower.getAddress(),
                        selectedBorrower.getPhone());
                borrowerInfoDisplay.setText(borrowerInfo);
            } else {
                borrowerInfoDisplay.setText("Borrower not found for ID: " + borrowerId);
            }
        });
        return new VBox(10, borrowerIdField, lookupButton, borrowerInfoDisplay);
    }

    private Node createCheckoutSection() {
        // Implement checkout functionality
        Button checkoutButton = new Button("Checkout");
        // Display area for checkout status
        TextArea checkoutStatus = new TextArea();
        checkoutStatus.setEditable(false);
        checkoutButton.setOnAction(event -> {
            handleCheckout(checkoutStatus);
        });
        return new VBox(10, checkoutButton, checkoutStatus);
    }

    private void handleCheckout(TextArea checkoutStatus) {
        if (selectedBorrower == null || selectedBooks.isEmpty()){
            checkoutStatus.setText("Please select a borrower and at least one book.");
            return;
        }
        String message = mediator.createBookLoans(selectedBooks, selectedBorrower.getCardId());
        checkoutStatus.setText(message);
    }


    // Additional helper methods as needed
}

