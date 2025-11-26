package com.mycompany.warehouse_desktop.controller.purchase;

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
import java.text.SimpleDateFormat;
import java.util.*;

public class ListPurchaseController {

    @FXML private TextField from;
    @FXML private TextField to;
    @FXML private Button search;

    @FXML private TableView<PurchaseTransaction> table;
    @FXML private TableColumn<PurchaseTransaction, Number> colId;
    @FXML private TableColumn<PurchaseTransaction, String> colThoigian;
    @FXML private TableColumn<PurchaseTransaction, Boolean> colDathanhtoan;
    @FXML private TableColumn<PurchaseTransaction, Number> colUserId;

    @FXML private Button getDetails;

    private final PurchaseTransactionService service = new PurchaseTransactionService();

    @FXML
    private void initialize() {
        setupColumns();
        loadList();

        search.setOnAction(e -> searchByDate());
        getDetails.setOnAction(e -> openDetail());
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
        PurchaseTransaction selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Purchase/DetailPurchaseView.fxml"));
            Parent root = loader.load();

            DetailsPurchaseTransactionController ctrl = loader.getController();
            ctrl.loadData(selected);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Chi tiết phiếu nhập");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
