package com.mycompany.warehouse_desktop.controller.sell;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SellHomeController {

    @FXML private Button btnCreate;
    @FXML private Button btnGetList;

    @FXML
    public void initialize() {
        btnCreate.setOnAction(e -> openCreateSell());
        btnGetList.setOnAction(e -> openListSell());
    }

    private void openCreateSell() {
        openWindow("/view/Sell/CreateSellView.fxml", "Tạo lượt bán");
    }

    private void openListSell() {
        openWindow("/view/Sell/ListSellView.fxml", "Danh sách lượt bán");
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
