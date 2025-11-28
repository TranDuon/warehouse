package com.mycompany.warehouse_desktop.controller.sell;

import com.mycompany.warehouse_desktop.db.sale_transaction.SaleTransaction;
import com.mycompany.warehouse_desktop.db.sale_transaction.SaleTransactionService;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class ListSellController {

    @FXML private TextField from;
    @FXML private TextField to;
    @FXML private Button btnSearch;

    @FXML private TableView<SaleTransaction> table;
    @FXML private TableColumn<SaleTransaction, Number> colId;
    @FXML private TableColumn<SaleTransaction, String> colThoigian;
    @FXML private TableColumn<SaleTransaction, Boolean> colDathanhtoan;
    @FXML private TableColumn<SaleTransaction, Number> colUserId;

    @FXML private Button btnDetails;

    private final SaleTransactionService service = new SaleTransactionService();

    @FXML
    private void initialize() {
        setupColumns();
        loadList();

        btnSearch.setOnAction(e -> searchByDate());
        btnDetails.setOnAction(e -> openDetail());
    }

    private void setupColumns() {
        colId.setCellValueFactory(c -> new SimpleLongProperty(c.getValue().getId()));
        colThoigian.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTimestamp().toString()));
        colDathanhtoan.setCellValueFactory(c -> new SimpleBooleanProperty(c.getValue().getIsPaid()));
        colUserId.setCellValueFactory(c -> new SimpleLongProperty(c.getValue().getUserIdEmployee()));
    }

    private void loadList() {
        table.getItems().setAll(service.getList(1, 100));
    }

    private void searchByDate() {
        try {
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

            Timestamp fromDate = new Timestamp(df.parse(from.getText()).getTime());
            Timestamp toDate = new Timestamp(df.parse(to.getText()).getTime());

            table.getItems().setAll(service.findByTime(fromDate, toDate));

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Ngày không đúng định dạng dd/MM/yyyy").show();
        }
    }

    private void openDetail() {
        SaleTransaction selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Sell/DetailSellView.fxml"));
            Parent root = loader.load();

            DetailSellController ctrl = loader.getController();
            ctrl.loadData(selected);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Chi tiết lượt bán");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
