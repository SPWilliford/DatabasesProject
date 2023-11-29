package edu.utdallas.d_team.library.gui;

import edu.utdallas.d_team.library.entity.Book;
import edu.utdallas.d_team.library.entity.Borrower;
import edu.utdallas.d_team.library.integration.LibraryGuiMediator;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
       //On the left is the book search section, on the right is the borrower info and checkout section
        Node bookSearchSection = createBookSearchSection();
        Node borrowerCheckoutSection = createBorrowerCheckoutSection();
       
        HBox topSection = new HBox(10, bookSearchSection, borrowerCheckoutSection);
        topSection.setAlignment(Pos.CENTER);
        
        Text bookCheckout = new Text("\n\nLibrary Checkout\n");
        bookCheckout.setFont(Font.font(20));
        
        VBox page = new VBox(5, bookCheckout, topSection);
        page.setAlignment(Pos.TOP_CENTER);
        VBox.setMargin(page, new Insets(30, 15, 0, 15));

        return page;
    }





    private Node createBookSearchSection() {
        //format book search section
        TextField searchField = new TextField();
        searchField.setPromptText("Find books by title, author, ISBN");
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
        
        //two boxes with an add or remove button for checkout process
        VBox searchFieldButton = new VBox(5, searchField, searchButton);
        VBox searchResultsWithButton = new VBox(5, searchFieldButton, searchResults, addButton);
        VBox selectedBooksViewWithButton = new VBox(5, selectedBooksView, removeButton);
        HBox searchSelected = new HBox(5, searchResultsWithButton, selectedBooksViewWithButton);

        VBox bookSearchLayout = new VBox(10);
        bookSearchLayout.getChildren().addAll(searchFieldButton, searchSelected);
        HBox.setMargin(bookSearchLayout, new Insets(30, 15, 0, 30));
        
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

    private Node createBorrowerCheckoutSection() {

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
        // Implement checkout functionality
        Button checkoutButton = new Button("Checkout selected books");
        // to display checkout status
        Label checkoutStatus = new Label();
        checkoutButton.setOnAction(event -> {
            handleCheckout(checkoutStatus);
        });
        
        VBox borrowerCheckout = new VBox(10, borrowerIdField, lookupButton, borrowerInfoDisplay, checkoutButton, checkoutStatus);
        borrowerCheckout.setAlignment(Pos.TOP_CENTER);
        HBox.setMargin(borrowerCheckout, new Insets(30, 100, 0, 30));

        return borrowerCheckout;
    }

    private void handleCheckout(Label checkoutStatus) {
        if (selectedBorrower == null || selectedBooks.isEmpty()){
            checkoutStatus.setText("Please select a borrower and at least one book.");
            return;
        }
        String message = mediator.createBookLoans(selectedBooks, selectedBorrower.getCardId());
        checkoutStatus.setText(message);
    }


}
