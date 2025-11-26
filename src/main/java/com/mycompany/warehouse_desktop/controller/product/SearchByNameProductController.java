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

public class SearchByNameProductController {

    @FXML
    private TextField nameKeyword;
    @FXML
    private Button searchButton;

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
    private Button getDetailsButton;

    private final ProductService productService = new ProductService();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        searchButton.setOnAction(e -> handleSearch());
        getDetailsButton.setOnAction(e -> openDetails());
    }

    private void handleSearch() {
        String keyword = nameKeyword.getText().trim();
        if (keyword.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Thiếu dữ liệu", "Hãy nhập từ khóa tên sản phẩm.");
            return;
        }

        List<ProductEntity> list = productService.findByName(keyword);
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
