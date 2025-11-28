package com.mycompany.warehouse_desktop.controller.purchase;

import com.mycompany.warehouse_desktop.db.product.ProductEntity;
import com.mycompany.warehouse_desktop.db.product.ProductService;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class SearchProductPurchaseController {

    @FXML private TextField nameKeyword;
    @FXML private Button btnSearch;
    @FXML private Button btnAdd;

    @FXML private TableView<ProductEntity> table;
    @FXML private TableColumn<ProductEntity, Number> colId;
    @FXML private TableColumn<ProductEntity, String> colTen;
    @FXML private TableColumn<ProductEntity, Number> colGia;
    @FXML private TableColumn<ProductEntity, String> colDonVi;
    @FXML private TableColumn<ProductEntity, String> colMoTa;
    @FXML private TableColumn<ProductEntity, Number> colSoLuong;

    private final ProductService productService = new ProductService();

    // callback
    private Consumer<ProductEntity> addCallback;

    public void setCallback(Consumer<ProductEntity> callback) {
        this.addCallback = callback;
    }

    @FXML
    private void initialize() {
        setupColumns();
        loadAllProducts();

        btnSearch.setOnAction(e -> search());
        btnAdd.setOnAction(e -> addSelected());
    }

    private void setupColumns() {
        colId.setCellValueFactory(c -> new SimpleLongProperty(c.getValue().getId()));
        colTen.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));
        colGia.setCellValueFactory(c -> new SimpleLongProperty(c.getValue().getPrice()));
        colDonVi.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getUnit()));
        colMoTa.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDescription()));
        colSoLuong.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getQuantity()));
    }

    private void loadAllProducts() {
        table.getItems().setAll(productService.getList(1, 200));
    }

    private void search() {
        String kw = nameKeyword.getText();

        if (kw == null || kw.isBlank()) {
            loadAllProducts();
            return;
        }

        table.getItems().setAll(productService.findByName(kw));
    }

    private void addSelected() {
        ProductEntity p = table.getSelectionModel().getSelectedItem();

        if (p == null) {
            Alert a = new Alert(Alert.AlertType.WARNING, "Vui lòng chọn sản phẩm!", ButtonType.OK);
            a.showAndWait();
            return;
        }

        if (addCallback != null) addCallback.accept(p);

        Stage stage = (Stage) btnAdd.getScene().getWindow();
        stage.close();
    }
}
