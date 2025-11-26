package com.mycompany.warehouse_desktop.controller.product;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ProductHomeController {

    @FXML
    private Button btnCreate;
    @FXML
    private Button btnGetList;
    @FXML
    private Button btnSearchByName;

    @FXML
    public void initialize() {
        btnCreate.setOnAction(e -> openCreateProduct());
        btnGetList.setOnAction(e -> openListProducts());
        btnSearchByName.setOnAction(e -> openSearchByName());
    }

    private void openCreateProduct() {
        openWindow("/view/product/CreateProductView.fxml", "Tạo vật phẩm");
    }

    private void openListProducts() {
        openWindow("/view/product/ListProductView.fxml", "Danh sách vật phẩm");
    }

    private void openSearchByName() {
        openWindow("/view/product/SearchByNameProductView.fxml", "Tìm kiếm vật phẩm");
    }

    private void openWindow(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
