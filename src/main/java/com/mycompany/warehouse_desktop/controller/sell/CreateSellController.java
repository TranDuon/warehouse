package com.mycompany.warehouse_desktop.controller.sell;

import com.mycompany.warehouse_desktop.db.product.ProductEntity;
import com.mycompany.warehouse_desktop.db.product.ProductService;
import com.mycompany.warehouse_desktop.db.sale_order_item.*;
import com.mycompany.warehouse_desktop.db.sale_transaction.*;
import javafx.beans.property.*;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Timestamp;

public class CreateSellController {

    @FXML private TableView<ProductEntity> table;
    @FXML private TableColumn<ProductEntity, Number> colStt;
    @FXML private TableColumn<ProductEntity, Number> colId;
    @FXML private TableColumn<ProductEntity, String> colTen;
    @FXML private TableColumn<ProductEntity, Number> colGia;
    @FXML private TableColumn<ProductEntity, String> colDonVi;
    @FXML private TableColumn<ProductEntity, Number> colSoluong;
    @FXML private TableColumn<ProductEntity, Number> colSoluongBan;

    @FXML private Button btnAddVatPham;
    @FXML private Button btnRemoveVatPham;
    @FXML private Button btnCreate;
    @FXML private Button btnCancel;

    private final ProductService productService = new ProductService();
    private final SaleTransactionService transactionService = new SaleTransactionService();
    private final SaleOrderItemService itemService = new SaleOrderItemService();

    private final ObservableList<ProductEntity> productList = FXCollections.observableArrayList();
    private final ObservableMap<Long, Integer> sellMap = FXCollections.observableHashMap();

    @FXML
    public void initialize() {
        setupColumns();

        btnAddVatPham.setOnAction(e -> openSearchPopup());
        btnRemoveVatPham.setOnAction(e -> removeProduct());
        btnCreate.setOnAction(e -> saveSell());
        btnCancel.setOnAction(e -> closeWindow());
    }

    private void setupColumns() {
        colStt.setCellValueFactory(c ->
                new SimpleIntegerProperty(table.getItems().indexOf(c.getValue()) + 1)
        );

        colId.setCellValueFactory(c -> new SimpleLongProperty(c.getValue().getId()));
        colTen.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));
        colGia.setCellValueFactory(c -> new SimpleLongProperty(c.getValue().getPrice()));
        colDonVi.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getUnit()));
        colSoluong.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getQuantity()));

        colSoluongBan.setCellFactory(col -> new TableCell<>() {
            private final TextField tf = new TextField();

            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                    return;
                }

                ProductEntity p = getTableView().getItems().get(getIndex());
                tf.setText(String.valueOf(sellMap.getOrDefault(p.getId(), 1)));

                tf.textProperty().addListener((obs, oldVal, newVal) -> {
                    try {
                        int qty = Integer.parseInt(newVal);
                        if (qty < 1) qty = 1;
                        if (qty > p.getQuantity()) qty = p.getQuantity();
                        sellMap.put(p.getId(), qty);
                    } catch (NumberFormatException ignored) {}
                });

                setGraphic(tf);
            }
        });
    }

    private void openSearchPopup() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Sell/SearchProductSellView.fxml"));
            Parent ui = loader.load();

            SearchProductSellController ctrl = loader.getController();
            ctrl.setAddCallback(this::addProductToTable);

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
        sellMap.put(p.getId(), 1);

        table.setItems(productList);
        table.refresh();
    }

    private void removeProduct() {
        ProductEntity selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            productList.remove(selected);
            sellMap.remove(selected.getId());
        }
    }

    private void saveSell() {
        try {
            if (productList.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Chưa chọn sản phẩm!", ButtonType.OK);
                alert.showAndWait();
                return;
            }

            SaleTransaction t = new SaleTransaction(
                    null,
                    new Timestamp(System.currentTimeMillis()),
                    true,
                    1L
            );

            SaleTransaction saved = transactionService.create(t);

            for (ProductEntity p : productList) {
                int qtySell = sellMap.getOrDefault(p.getId(), 1);
                if (qtySell < 1) continue;

                SaleOrderItem item = new SaleOrderItem(
                        new SaleOrderItemId(p.getId(), saved.getId()),
                        qtySell,
                        p.getPrice()
                );

                itemService.create(item);

                p.setQuantity(p.getQuantity() - qtySell);
                productService.update(p.getId(), p);
            }

            Alert a = new Alert(Alert.AlertType.INFORMATION, "Tạo lượt bán thành công!", ButtonType.OK);
            a.showAndWait();
            closeWindow();

        } catch (Exception e) {
            e.printStackTrace();
            Alert a = new Alert(Alert.AlertType.ERROR, "Có lỗi xảy ra khi tạo lượt bán!", ButtonType.OK);
            a.showAndWait();
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
