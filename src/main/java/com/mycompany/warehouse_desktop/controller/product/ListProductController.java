package com.mycompany.warehouse_desktop.controller.product;

import com.mycompany.warehouse_desktop.db.product.ProductEntity;
import com.mycompany.warehouse_desktop.db.product.ProductService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ListProductController {

    @FXML
    private TextField pageNum;
    @FXML
    private TextField sizePage;

    @FXML
    private TableView<ProductEntity> tableView;
    @FXML
    private TableColumn<ProductEntity, Long> colId;
    @FXML
    private TableColumn<ProductEntity, String> colName;
    @FXML
    private TableColumn<ProductEntity, Long> colPrice;
    @FXML
    private TableColumn<ProductEntity, String> colUnit;
    @FXML
    private TableColumn<ProductEntity, String> colDescription;
    @FXML
    private TableColumn<ProductEntity, Integer> colQuantity;

    @FXML
    private Button getListButton;
    @FXML
    private Button getDetailsButton;

    private final ProductService productService = new ProductService();

    @FXML
    public void initialize() {
        // map cột với thuộc tính entity
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        // giá trị mặc định phân trang
        pageNum.setText("1");
        sizePage.setText("10");

        getListButton.setOnAction(e -> loadData());
        getDetailsButton.setOnAction(e -> openDetails());
    }

    private void loadData() {
        int page = 1;
        int size = 10;

        try {
            if (!pageNum.getText().trim().isEmpty()) {
                page = Integer.parseInt(pageNum.getText().trim());
            }
            if (!sizePage.getText().trim().isEmpty()) {
                size = Integer.parseInt(sizePage.getText().trim());
            }
        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.WARNING, "Lỗi dữ liệu", "Trang và kích cỡ phải là số nguyên.");
            return;
        }

        List<ProductEntity> list = productService.getList(page, size);
        tableView.setItems(FXCollections.observableArrayList(list));
    }

    private void openDetails() {
        ProductEntity selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.INFORMATION, "Chưa chọn", "Hãy chọn một vật phẩm trong bảng.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/product/DetailProductView.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Chi tiết vật phẩm");
            stage.setScene(new Scene(loader.load()));

            DetailProductController controller = loader.getController();
            controller.setProduct(selected);

            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
