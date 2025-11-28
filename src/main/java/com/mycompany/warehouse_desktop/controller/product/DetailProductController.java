package com.mycompany.warehouse_desktop.controller.product;

import com.mycompany.warehouse_desktop.db.product.ProductEntity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class DetailProductController {

    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField unitField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextArea descriptionField;

    @FXML
    private Button updateButton;
    @FXML
    private Button removeButton;

    private ProductEntity currentProduct;

    @FXML
    public void initialize() {
        updateButton.setOnAction(e -> openUpdate());
        removeButton.setOnAction(e -> openDelete());
    }

    public void setProduct(ProductEntity product) {
        this.currentProduct = product;
        if (product != null) {
            idField.setText(String.valueOf(product.getId()));
            nameField.setText(product.getName());
            priceField.setText(String.valueOf(product.getPrice()));
            unitField.setText(product.getUnit());
            quantityField.setText(String.valueOf(product.getQuantity()));
            descriptionField.setText(product.getDescription());
        }
    }

    private void openUpdate() {
        if (currentProduct == null) return;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/product/UpdateProductView.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Cập nhật vật phẩm");
            stage.setScene(new Scene(loader.load()));

            UpdateProductController controller = loader.getController();
            controller.setProduct(currentProduct);

            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void openDelete() {
        if (currentProduct == null) return;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/product/DeleteProductView.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Xóa vật phẩm");
            stage.setScene(new Scene(loader.load()));
            com.mycompany.warehouse_desktop.controller.product.DeleteProductController controller =
                    loader.getController();
            controller.setProduct(currentProduct);

            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
