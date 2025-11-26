package com.mycompany.warehouse_desktop.controller.product;

import com.mycompany.warehouse_desktop.db.product.ProductEntity;
import com.mycompany.warehouse_desktop.db.product.ProductService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class UpdateProductController {

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
    private Button cancelButton;

    private final ProductService productService = new ProductService();
    private ProductEntity currentProduct;

    @FXML
    public void initialize() {
        updateButton.setOnAction(e -> handleUpdate());
        cancelButton.setOnAction(e -> closeWindow());
    }

    public void setProduct(ProductEntity product) {
        this.currentProduct = product;
        if (product != null) {
            idField.setText(String.valueOf(product.getId()));
            idField.setEditable(false);

            nameField.setText(product.getName());
            priceField.setText(String.valueOf(product.getPrice()));
            unitField.setText(product.getUnit());
            quantityField.setText(String.valueOf(product.getQuantity()));
            descriptionField.setText(product.getDescription());
        }
    }

    private void handleUpdate() {
        if (currentProduct == null) return;

        String name = nameField.getText().trim();
        String unit = unitField.getText().trim();
        String desc = descriptionField.getText().trim();
        String priceStr = priceField.getText().trim();
        String quantityStr = quantityField.getText().trim();

        if (name.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Lỗi dữ liệu", "Tên vật phẩm không được để trống");
            return;
        }

        long price;
        int quantity;
        try {
            price = Long.parseLong(priceStr);
            if (price < 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.WARNING, "Lỗi dữ liệu", "Giá phải là số nguyên không âm");
            return;
        }

        try {
            quantity = Integer.parseInt(quantityStr);
            if (quantity < 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.WARNING, "Lỗi dữ liệu", "Số lượng phải là số nguyên không âm");
            return;
        }

        currentProduct.setName(name);
        currentProduct.setUnit(unit);
        currentProduct.setDescription(desc);
        currentProduct.setPrice(price);
        currentProduct.setQuantity(quantity);

        ProductEntity updated = productService.update(currentProduct.getId(), currentProduct);
        if (updated != null) {
            showAlert(Alert.AlertType.INFORMATION, "Thành công", "Cập nhật vật phẩm thành công!");
            closeWindow();
        } else {
            showAlert(Alert.AlertType.ERROR, "Thất bại", "Không thể cập nhật vật phẩm.");
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
