package com.mycompany.warehouse_desktop.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import java.io.IOException;

public class HomeController {

    @FXML
    private VBox mainContent;

    public void openProductPage() {
        loadPage("/view/Product/ProductHomeView.fxml");
    }

    public void openPurchasePage() {
        loadPage("/view/Purchase/PurchaseHomeView.fxml");
    }

    public void openSellPage() {
        loadPage("/view/Sell/SellHomeView.fxml");
    }

    public void openHomePage() {
        loadPage("/view/Home/HomeView.fxml");
    }

    private void loadPage(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node content = loader.load();

            mainContent.getChildren().clear();
            mainContent.getChildren().add(content);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Cannot load FXML: " + fxmlPath);
        }
    }
}
