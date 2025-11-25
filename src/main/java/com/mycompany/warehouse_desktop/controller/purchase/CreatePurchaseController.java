package com.mycompany.warehouse_desktop.controller.purchase;

import com.mycompany.warehouse_desktop.db.product.ProductEntity;
import com.mycompany.warehouse_desktop.db.product.ProductService;
import com.mycompany.warehouse_desktop.db.purchase_order_item.PurchaseOrderItem;
import com.mycompany.warehouse_desktop.db.purchase_order_item.PurchaseOrderItemId;
import com.mycompany.warehouse_desktop.db.purchase_order_item.PurchaseOrderItemService;
import com.mycompany.warehouse_desktop.db.purchase_transaction.PurchaseTransaction;
import com.mycompany.warehouse_desktop.db.purchase_transaction.PurchaseTransactionService;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

public class CreatePurchaseController {

    @FXML
    private TableView<RowItem> table;
    @FXML
    private TableColumn<RowItem, Number> colStt;
    @FXML
    private TableColumn<RowItem, Long> colId;
    @FXML
    private TableColumn<RowItem, String> colTen;
    @FXML
    private TableColumn<RowItem, Long> colGia;
    @FXML
    private TableColumn<RowItem, String> colDonVi;
    @FXML
    private TableColumn<RowItem, Integer> colConKho;
    @FXML
    private TableColumn<RowItem, Integer> colSoLuongNhap;

    @FXML
    private Button btnAddProduct;

    @FXML
    private Button btnRemoveProduct;

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnCancel;

    private final ObservableList<RowItem> items = FXCollections.observableArrayList();

    private final ProductService productService = new ProductService();
    private final PurchaseTransactionService transactionService = new PurchaseTransactionService();
    private final PurchaseOrderItemService itemService = new PurchaseOrderItemService();

    @FXML
    public void initialize() {
        initTable();
        table.setItems(items);

        btnAddProduct.setOnAction(e -> openSearchProductWindow());
        btnRemoveProduct.setOnAction(e -> onRemoveProduct());
        btnCreate.setOnAction(e -> onCreatePurchase());
    }

    private void initTable() {
        colStt.setCellValueFactory(cell ->
                new ReadOnlyIntegerWrapper(table.getItems().indexOf(cell.getValue()) + 1));

        colId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colTen.setCellValueFactory(new PropertyValueFactory<>("name"));
        colGia.setCellValueFactory(new PropertyValueFactory<>("price"));
        colDonVi.setCellValueFactory(new PropertyValueFactory<>("unit"));
        colConKho.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colSoLuongNhap.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

    // =============================================================
    // mở SearchProductPurchaseView.fxml
    // =============================================================
    private void openSearchProductWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/SearchProductPurchaseView.fxml")
            );
            Scene scene = new Scene(loader.load());

            SearchProductPurchaseController searchController = loader.getController();
            searchController.setOnProductSelected(this::addProductToTable);

            Stage stage = new Stage();
            stage.setTitle("Tìm kiếm sản phẩm");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
            showError("Không thể mở cửa sổ tìm kiếm!");
        }
    }

    // =============================================================
    // THÊM SẢN PHẨM VÀO BẢNG
    // =============================================================
    private void addProductToTable(ProductEntity p) {
        TextInputDialog dialog = new TextInputDialog("1");
        dialog.setHeaderText("Nhập số lượng cho sản phẩm: " + p.getName());
        dialog.setContentText("Số lượng nhập:");

        int qty;
        try {
            qty = Integer.parseInt(dialog.showAndWait().orElse("1"));
        } catch (NumberFormatException e) {
            showError("Số lượng không hợp lệ!");
            return;
        }

        items.add(new RowItem(
                p.getId(),
                p.getName(),
                p.getPrice(),
                p.getUnit(),
                p.getQuantity() != null ? p.getQuantity() : 0,
                qty
        ));
    }

    // =============================================================
    // XÓA SẢN PHẨM
    // =============================================================
    private void onRemoveProduct() {
        RowItem selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Hãy chọn dòng muốn xoá.");
            return;
        }
        items.remove(selected);
    }

    // =============================================================
    // TẠO PHIẾU NHẬP
    // =============================================================
    private void onCreatePurchase() {
        if (items.isEmpty()) {
            showError("Chưa có sản phẩm nào trong lượt nhập.");
            return;
        }

        Long userIdEmployee = 1L; // TODO: lấy từ session đăng nhập

        PurchaseTransaction transaction = new PurchaseTransaction(
                null,
                Timestamp.from(Instant.now()),
                false,
                userIdEmployee
        );

        transaction = transactionService.create(transaction);
        if (transaction == null) {
            showError("Không thể tạo phiếu nhập!");
            return;
        }

        Long transactionId = transaction.getId();

        for (RowItem row : items) {
            PurchaseOrderItem item = new PurchaseOrderItem(
                    new PurchaseOrderItemId(row.getProductId(), transactionId),
                    row.getQuantity(),
                    row.getPrice()
            );
            itemService.create(item);
        }

        showInfo("Tạo lượt nhập hàng thành công (ID = " + transactionId + ")");
        items.clear();
    }

    // =============================================================
    // DTO HIỂN THỊ TRONG BẢNG
    // =============================================================
    public static class RowItem {
        private final LongProperty productId;
        private final StringProperty name;
        private final LongProperty price;
        private final StringProperty unit;
        private final IntegerProperty stock;
        private final IntegerProperty quantity;

        public RowItem(Long productId, String name, Long price,
                       String unit, Integer stock, Integer quantity) {
            this.productId = new SimpleLongProperty(productId);
            this.name = new SimpleStringProperty(name);
            this.price = new SimpleLongProperty(price);
            this.unit = new SimpleStringProperty(unit);
            this.stock = new SimpleIntegerProperty(stock);
            this.quantity = new SimpleIntegerProperty(quantity);
        }

        public long getProductId() { return productId.get(); }
        public LongProperty productIdProperty() { return productId; }

        public String getName() { return name.get(); }
        public StringProperty nameProperty() { return name; }

        public long getPrice() { return price.get(); }
        public LongProperty priceProperty() { return price; }

        public String getUnit() { return unit.get(); }
        public StringProperty unitProperty() { return unit; }

        public int getStock() { return stock.get(); }
        public IntegerProperty stockProperty() { return stock; }

        public int getQuantity() { return quantity.get(); }
        public IntegerProperty quantityProperty() { return quantity; }
    }


    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        alert.showAndWait();
    }

    private void showInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.showAndWait();
    }
}
