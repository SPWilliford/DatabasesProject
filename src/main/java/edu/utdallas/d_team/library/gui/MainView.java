package edu.utdallas.d_team.library.gui;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

public class MainView extends BorderPane {

    public MainView() {

        TabPane tabPane = new TabPane();

        Tab searchTab = new Tab("Search Books", new SearchTab().getContent());
        searchTab.setClosable(false);
        tabPane.getTabs().add(searchTab);

        Tab checkoutTab = new Tab("Check Out", new CheckoutTab().getContent());
        checkoutTab.setClosable(false);
        tabPane.getTabs().add(checkoutTab);

        Tab checkinTab = new Tab("Check In", new CheckinTab().getContent());
        checkinTab.setClosable(false);
        tabPane.getTabs().add(checkinTab);

        Tab borrowerTab = new Tab("Borrowers", new BorrowerTab().getContent());
        borrowerTab.setClosable(false);
        tabPane.getTabs().add(borrowerTab);

        this.setCenter(tabPane);
    }

}
