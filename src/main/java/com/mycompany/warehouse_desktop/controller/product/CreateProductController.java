package com.mycompany.warehouse_desktop.controller.product;

import com.mycompany.warehouse_desktop.db.product.ProductEntity;
import com.mycompany.warehouse_desktop.db.product.ProductService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CreateProductController {

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
    private Button createButton;
    @FXML
    private Button cancelButton;

    private final ProductService productService = new ProductService();

    @FXML
    public void initialize() {
        idField.setText("Tự sinh");
        idField.setEditable(false);

        createButton.setOnAction(e -> handleCreate());
        cancelButton.setOnAction(e -> closeWindow());
    }

    private void handleCreate() {
        String name = tenField.getText().trim();
        String unit = donviField.getText().trim();
        String desc = motaField.getText().trim();
        String priceStr = giaField.getText().trim();
        String quantityStr = soluongField.getText().trim();

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

        ProductEntity p = new ProductEntity(
                null,
                name,
                price,
                unit,
                desc,
                quantity
        );

        ProductEntity created = productService.create(p);
        if (created != null) {
            showAlert(Alert.AlertType.INFORMATION, "Thành công", "Tạo vật phẩm thành công!");
            closeWindow();
        } else {
            showAlert(Alert.AlertType.ERROR, "Thất bại", "Không thể tạo vật phẩm. Kiểm tra lại kết nối CSDL.");
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
