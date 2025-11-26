package com.mycompany.warehouse_desktop.controller.purchase;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PurchaseHomeController {

    @FXML private Button btnCreate;
    @FXML private Button btnGetList;

    @FXML
    public void initialize() {
        btnCreate.setOnAction(e -> openCreatePurchase());
        btnGetList.setOnAction(e -> openListPurchase());
    }

    private void openCreatePurchase() {
        openWindow("/view/Purchase/CreatePurchaseView.fxml", "Tạo lượt nhập");
    }

    private void openListPurchase() {
        openWindow("/view/Purchase/ListPurchaseView.fxml", "Danh sách lượt nhập");
    }

    private void openWindow(String path, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
