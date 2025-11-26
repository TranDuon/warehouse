package com.mycompany.warehouse_desktop.controller.purchase;

import com.mycompany.warehouse_desktop.db.product.ProductService;
import com.mycompany.warehouse_desktop.db.purchase_order_item.PurchaseOrderItem;
import com.mycompany.warehouse_desktop.db.purchase_order_item.PurchaseOrderItemService;
import com.mycompany.warehouse_desktop.db.purchase_transaction.PurchaseTransaction;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class DetailsPurchaseTransactionController {

    @FXML private TextField id;
    @FXML private TextField timestamp;
    @FXML private TextField isPaid;
    @FXML private TextField useridEmployee;

    @FXML private TableView<PurchaseOrderItem> table;
    @FXML private TableColumn<PurchaseOrderItem, Number> colProductId;
    @FXML private TableColumn<PurchaseOrderItem, String> colProductName;
    @FXML private TableColumn<PurchaseOrderItem, Number> colTransactionId;
    @FXML private TableColumn<PurchaseOrderItem, Number> colQuantity;
    @FXML private TableColumn<PurchaseOrderItem, Number> colPrice;

    private final PurchaseOrderItemService itemService = new PurchaseOrderItemService();
    private final ProductService productService = new ProductService();

    @FXML
    private void initialize() {
        setupColumns();
    }

    private void setupColumns() {
        colProductId.setCellValueFactory(c -> new SimpleLongProperty(c.getValue().getId().getProductId()));
        colTransactionId.setCellValueFactory(c -> new SimpleLongProperty(c.getValue().getId().getPurchaseTransactionId()));
        colQuantity.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getQuantity()));
        colPrice.setCellValueFactory(c -> new SimpleLongProperty(c.getValue().getPrice()));

        colProductName.setCellValueFactory(c -> {
            Long id = c.getValue().getId().getProductId();
            return new SimpleStringProperty(productService.findById(id).getName());
        });
    }

    public void loadData(PurchaseTransaction t) {
        id.setText(String.valueOf(t.getId()));
        timestamp.setText(t.getTimestamp().toString());
        isPaid.setText(t.getIsPaid() ? "Yes" : "No");
        useridEmployee.setText(String.valueOf(t.getUserIdEmployee()));

        List<PurchaseOrderItem> list = itemService.findByPurchaseTransactionId(t.getId());
        table.getItems().setAll(list);
    }
}
