package com.mycompany.warehouse_desktop.controller.purchase;

import com.mycompany.warehouse_desktop.db.product.ProductEntity;
import com.mycompany.warehouse_desktop.db.product.ProductService;
import com.mycompany.warehouse_desktop.db.purchase_order_item.PurchaseOrderItem;
import com.mycompany.warehouse_desktop.db.purchase_order_item.PurchaseOrderItemService;
import com.mycompany.warehouse_desktop.db.purchase_transaction.PurchaseTransaction;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class DetailsPurchaseTransactionController {

    @FXML
    private TextField id;

    @FXML
    private TextField timestamp;

    @FXML
    private TextField isPaid;

    @FXML
    private TextField useridEmployee;

    @FXML
    private TableView<DetailRow> table;

    @FXML
    private TableColumn<DetailRow, Long> colProductId;

    @FXML
    private TableColumn<DetailRow, String> colProductName;

    @FXML
    private TableColumn<DetailRow, Long> colTransactionId;

    @FXML
    private TableColumn<DetailRow, Integer> colQuantity;

    @FXML
    private TableColumn<DetailRow, Long> colPrice;

    private final PurchaseOrderItemService itemService = new PurchaseOrderItemService();
    private final ProductService productService = new ProductService();

    private PurchaseTransaction purchaseTransaction;

    @FXML
    public void initialize() {
        initTable();
    }

    private void initTable() {
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colTransactionId.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Hàm này được gọi từ ListPurchaseController
     */
    public void setPurchaseTransaction(PurchaseTransaction transaction) {
        this.purchaseTransaction = transaction;

        // set thông tin lên textfield
        id.setText(String.valueOf(transaction.getId()));
        timestamp.setText(transaction.getTimestamp() != null
                ? transaction.getTimestamp().toString()
                : "");
        isPaid.setText(Boolean.TRUE.equals(transaction.getIsPaid()) ? "Đã thanh toán" : "Chưa thanh toán");
        useridEmployee.setText(
                transaction.getUserIdEmployee() != null
                        ? transaction.getUserIdEmployee().toString()
                        : ""
        );

        loadItems();
    }

    private void loadItems() {
        if (purchaseTransaction == null) return;

        List<PurchaseOrderItem> items =
                itemService.findByPurchaseTransactionId(purchaseTransaction.getId());

        List<DetailRow> rows = new ArrayList<>();

        if (items != null) {
            for (PurchaseOrderItem item : items) {
                Long productId = item.getId().getProductId();
                ProductEntity product = productService.findById(productId);

                String productName = product != null ? product.getName() : "(unknown)";

                rows.add(new DetailRow(
                        productId,
                        productName,
                        item.getId().getPurchaseTransactionId(),
                        item.getQuantity(),
                        item.getPrice()
                ));
            }
        }

        table.getItems().setAll(rows);
    }

    // DTO hiển thị trong bảng
    public static class DetailRow {
        private final LongProperty productId;
        private final StringProperty productName;
        private final LongProperty transactionId;
        private final IntegerProperty quantity;
        private final LongProperty price;

        public DetailRow(Long productId, String productName,
                         Long transactionId, Integer quantity, Long price) {
            this.productId = new SimpleLongProperty(productId);
            this.productName = new SimpleStringProperty(productName);
            this.transactionId = new SimpleLongProperty(transactionId);
            this.quantity = new SimpleIntegerProperty(quantity);
            this.price = new SimpleLongProperty(price);
        }

        public long getProductId() { return productId.get(); }
        public String getProductName() { return productName.get(); }
        public long getTransactionId() { return transactionId.get(); }
        public int getQuantity() { return quantity.get(); }
        public long getPrice() { return price.get(); }

        public LongProperty productIdProperty() { return productId; }
        public StringProperty productNameProperty() { return productName; }
        public LongProperty transactionIdProperty() { return transactionId; }
        public IntegerProperty quantityProperty() { return quantity; }
        public LongProperty priceProperty() { return price; }
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        alert.showAndWait();
    }
}
