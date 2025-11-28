package com.mycompany.warehouse_desktop.controller.sell;

import com.mycompany.warehouse_desktop.db.product.ProductService;
import com.mycompany.warehouse_desktop.db.sale_order_item.SaleOrderItem;
import com.mycompany.warehouse_desktop.db.sale_order_item.SaleOrderItemService;
import com.mycompany.warehouse_desktop.db.sale_transaction.SaleTransaction;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class DetailSellController {

    @FXML private TextField id;
    @FXML private TextField thoigian;
    @FXML private TextField dathanhtoan;
    @FXML private TextField idnhanvien;

    @FXML private TableView<SaleOrderItem> table;
    @FXML private TableColumn<SaleOrderItem, Number> colIdVatPham;
    @FXML private TableColumn<SaleOrderItem, String> colTen;
    @FXML private TableColumn<SaleOrderItem, Number> colIdLuotBan;
    @FXML private TableColumn<SaleOrderItem, Number> colSoLuong;
    @FXML private TableColumn<SaleOrderItem, Number> colGia;

    private final SaleOrderItemService itemService = new SaleOrderItemService();
    private final ProductService productService = new ProductService();

    @FXML
    private void initialize() {
        setupColumns();
    }

    private void setupColumns() {
        colIdVatPham.setCellValueFactory(c ->
                new SimpleLongProperty(c.getValue().getId().getProductId())
        );

        colIdLuotBan.setCellValueFactory(c ->
                new SimpleLongProperty(c.getValue().getId().getSaleTransactionId())
        );

        colSoLuong.setCellValueFactory(c ->
                new SimpleIntegerProperty(c.getValue().getQuantity())
        );

        colGia.setCellValueFactory(c ->
                new SimpleLongProperty(c.getValue().getPrice())
        );

        colTen.setCellValueFactory(c -> {
            Long pid = c.getValue().getId().getProductId();
            return new SimpleStringProperty(productService.findById(pid).getName());
        });
    }

    public void loadData(SaleTransaction t) {
        id.setText(String.valueOf(t.getId()));
        thoigian.setText(t.getTimestamp().toString());
        dathanhtoan.setText(t.getIsPaid() ? "Yes" : "No");
        idnhanvien.setText(String.valueOf(t.getUserIdEmployee()));

        List<SaleOrderItem> list = itemService.findBySaleTransactionId(t.getId());
        table.getItems().setAll(list);
    }
}
