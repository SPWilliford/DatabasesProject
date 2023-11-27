package edu.utdallas.d_team.library.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

@Component
public class MainView extends BorderPane {

    @Autowired
    private final SearchTab searchTab;
    @Autowired
    private final CheckoutTab checkoutTab;
    @Autowired
    private final CheckinTab checkinTab;
    @Autowired
    private final BorrowerTab borrowerTab;
    private final FinesTab finesTab;


    @Autowired
    public MainView(SearchTab searchTab, CheckoutTab checkoutTab, CheckinTab checkinTab, BorrowerTab borrowerTab, FinesTab finesTab) {
        this.searchTab = searchTab;
        this.checkoutTab = checkoutTab;
        this.checkinTab = checkinTab;
        this.borrowerTab =  borrowerTab;
        this.finesTab = finesTab;

        initializeTabs();
    }

    private void initializeTabs() {
        TabPane tabPane = new TabPane();

        Tab searchBookTab = new Tab("Search", searchTab.getContent());
        searchBookTab.setClosable(false);
        tabPane.getTabs().add(searchBookTab);

        Tab checkoutBookTab = new Tab("Check Out", checkoutTab.getContent());
        checkoutBookTab.setClosable(false);
        tabPane.getTabs().add(checkoutBookTab);

        Tab checkinBookTab = new Tab("Check In", checkinTab.getContent());
        checkinBookTab.setClosable(false);
        tabPane.getTabs().add(checkinBookTab);

        Tab borrowerInfoTab = new Tab("Borrowers", borrowerTab.getContent());
        borrowerInfoTab.setClosable(false);
        tabPane.getTabs().add(borrowerInfoTab);

        Tab finesManagementTab = new Tab("Fines", finesTab.getContent());
        finesManagementTab.setClosable(false);
        tabPane.getTabs().add(finesManagementTab);

        this.setCenter(tabPane);
    }


}
