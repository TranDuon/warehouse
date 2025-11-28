package com.mycompany.warehouse_desktop.controller.purchase;

import com.mycompany.warehouse_desktop.db.product.ProductEntity;
import com.mycompany.warehouse_desktop.db.product.ProductService;
import com.mycompany.warehouse_desktop.db.purchase_order_item.PurchaseOrderItem;
import com.mycompany.warehouse_desktop.db.purchase_order_item.PurchaseOrderItemId;
import com.mycompany.warehouse_desktop.db.purchase_order_item.PurchaseOrderItemService;
import com.mycompany.warehouse_desktop.db.purchase_transaction.PurchaseTransaction;
import com.mycompany.warehouse_desktop.db.purchase_transaction.PurchaseTransactionService;
import javafx.beans.property.*;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.util.List;

public class CreatePurchaseController {

    @FXML private TableView<ProductEntity> table;
    @FXML private TableColumn<ProductEntity, Number> colStt;
    @FXML private TableColumn<ProductEntity, Number> colId;
    @FXML private TableColumn<ProductEntity, String> colTen;
    @FXML private TableColumn<ProductEntity, Number> colGia;
    @FXML private TableColumn<ProductEntity, String> colDonVi;
    @FXML private TableColumn<ProductEntity, Number> colConKho;
    @FXML private TableColumn<ProductEntity, Number> colSoLuongNhap;

    @FXML private Button btnAddProduct;
    @FXML private Button btnRemoveProduct;
    @FXML private Button btnCreate;
    @FXML private Button btnCancel;

    private final ProductService productService = new ProductService();
    private final PurchaseTransactionService transactionService = new PurchaseTransactionService();
    private final PurchaseOrderItemService itemService = new PurchaseOrderItemService();

    private final ObservableList<ProductEntity> productList = FXCollections.observableArrayList();
    private final ObservableMap<Long, Integer> inputMap = FXCollections.observableHashMap();

    @FXML
    private void initialize() {
        setupColumns();
        btnAddProduct.setOnAction(e -> openSearchPopup());
        btnRemoveProduct.setOnAction(e -> removeProduct());
        btnCreate.setOnAction(e -> savePurchase());
        btnCancel.setOnAction(e -> closeWindow());
    }

    private void setupColumns() {
        colStt.setCellValueFactory(c -> new SimpleIntegerProperty(table.getItems().indexOf(c.getValue()) + 1));
        colId.setCellValueFactory(c -> new SimpleLongProperty(c.getValue().getId()));
        colTen.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));
        colGia.setCellValueFactory(c -> new SimpleLongProperty(c.getValue().getPrice()));
        colDonVi.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getUnit()));
        colConKho.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getQuantity()));

        colSoLuongNhap.setCellFactory(col -> new TableCell<>() {
            private final TextField tf = new TextField();

            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                    return;
                }

                ProductEntity product = getTableView().getItems().get(getIndex());
                tf.setText(String.valueOf(inputMap.getOrDefault(product.getId(), 0)));

                tf.textProperty().addListener((obs, old, newVal) -> {
                    try {
                        int qty = Integer.parseInt(newVal);
                        inputMap.put(product.getId(), qty);
                    } catch (NumberFormatException ignored) {}
                });

                setGraphic(tf);
            }
        });
    }

    private void openSearchPopup() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Purchase/SearchProductPurchaseView.fxml"));
            Parent ui = loader.load();

            SearchProductPurchaseController searchController = loader.getController();

            // callback khi chọn 1 sản phẩm
            searchController.setCallback(this::addProductToTable);

            Stage st = new Stage();
            st.setScene(new Scene(ui));
            st.setTitle("Tìm sản phẩm");
            st.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addProductToTable(ProductEntity p) {
        for (ProductEntity pe : productList) {
            if (pe.getId().equals(p.getId())) {
                Alert a = new Alert(Alert.AlertType.WARNING, "Sản phẩm đã có trong danh sách!", ButtonType.OK);
                a.showAndWait();
                return;
            }
        }

        productList.add(p);
        table.setItems(productList);
    }

    private void removeProduct() {
        ProductEntity selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            productList.remove(selected);
            inputMap.remove(selected.getId());
        }
    }

    private void savePurchase() {
        try {
            PurchaseTransaction t = new PurchaseTransaction(
                    null,
                    new Timestamp(System.currentTimeMillis()),
                    true,   // isPaid
                    1L      // user ID
            );

            PurchaseTransaction saved = transactionService.create(t);

            for (ProductEntity p : productList) {
                int qty = inputMap.getOrDefault(p.getId(), 0);
                if (qty <= 0) continue;

                // tạo item
                PurchaseOrderItem item = new PurchaseOrderItem(
                        new PurchaseOrderItemId(p.getId(), saved.getId()),
                        qty,
                        p.getPrice()
                );

                itemService.create(item);

                // cập nhật kho
                p.setQuantity(p.getQuantity() + qty);
                productService.update(p.getId(), p);
            }

            Alert a = new Alert(Alert.AlertType.INFORMATION, "Tạo phiếu nhập thành công!", ButtonType.OK);
            a.showAndWait();
            closeWindow();

        } catch (Exception e) {
            e.printStackTrace();
            Alert a = new Alert(Alert.AlertType.ERROR, "Có lỗi xảy ra khi lưu phiếu nhập!", ButtonType.OK);
            a.showAndWait();
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
