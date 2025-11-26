package com.mycompany.warehouse_desktop.controller.product;

import com.mycompany.warehouse_desktop.db.product.ProductEntity;
import com.mycompany.warehouse_desktop.db.product.ProductService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class DeleteProductController {

    @FXML
    private TextField idField;
    @FXML
    private TextField tenField;
    @FXML
    private TextField giaField;
    @FXML
    private TextField donviField;
    @FXML
    private TextField soluongField;
    @FXML
    private TextArea motaField;

    @FXML
    private Button deleteButton;
    @FXML
    private Button cancelButton;

    private final ProductService productService = new ProductService();
    private ProductEntity currentProduct;

    @FXML
    public void initialize() {
        deleteButton.setOnAction(e -> handleDelete());
        cancelButton.setOnAction(e -> closeWindow());
    }

    public void setProduct(ProductEntity product) {
        this.currentProduct = product;
        if (product != null) {
            idField.setText(String.valueOf(product.getId()));
            tenField.setText(product.getName());
            giaField.setText(String.valueOf(product.getPrice()));
            donviField.setText(product.getUnit());
            soluongField.setText(String.valueOf(product.getQuantity()));
            motaField.setText(product.getDescription());

            idField.setEditable(false);
            tenField.setEditable(false);
            giaField.setEditable(false);
            donviField.setEditable(false);
            soluongField.setEditable(false);
            motaField.setEditable(false);
        }
    }

    private void handleDelete() {
        if (currentProduct == null) return;

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Xác nhận xóa");
        confirm.setHeaderText(null);
        confirm.setContentText("Bạn có chắc muốn xóa vật phẩm này?");
        var result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean success = productService.delete(currentProduct.getId());
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Xóa vật phẩm thành công!");
                closeWindow();
            } else {
                showAlert(Alert.AlertType.ERROR, "Thất bại", "Không thể xóa vật phẩm.");
            }
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
