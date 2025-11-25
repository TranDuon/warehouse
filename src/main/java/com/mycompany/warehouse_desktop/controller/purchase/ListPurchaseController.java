package com.mycompany.warehouse_desktop.controller.purchase;

import com.mycompany.warehouse_desktop.db.purchase_transaction.PurchaseTransaction;
import com.mycompany.warehouse_desktop.db.purchase_transaction.PurchaseTransactionService;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ListPurchaseController {

    @FXML
    private TextField from;

    @FXML
    private TextField to;

    @FXML
    private Button search;

    @FXML
    private TableView<PurchaseTransaction> table;

    @FXML
    private TableColumn<PurchaseTransaction, Long> colId;

    @FXML
    private TableColumn<PurchaseTransaction, String> colThoigian;

    @FXML
    private TableColumn<PurchaseTransaction, Boolean> colDathanhtoan;

    @FXML
    private TableColumn<PurchaseTransaction, Long> colUserId;

    @FXML
    private Button getDetails;

    private final PurchaseTransactionService purchaseTransactionService =
            new PurchaseTransactionService();

    private final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    @FXML
    public void initialize() {
        initTable();
        loadAll();

        search.setOnAction(this::onSearch);
        getDetails.setOnAction(this::onGetDetails);
    }

    private void initTable() {
        colId.setCellValueFactory(cell ->
                new SimpleLongProperty(cell.getValue().getId()).asObject());

        colThoigian.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        cell.getValue().getTimestamp() != null
                                ? cell.getValue().getTimestamp().toString()
                                : ""
                ));

        colDathanhtoan.setCellValueFactory(cell ->
                new SimpleBooleanProperty(
                        Boolean.TRUE.equals(cell.getValue().getIsPaid())
                ).asObject());

        colUserId.setCellValueFactory(cell ->
                new SimpleLongProperty(
                        cell.getValue().getUserIdEmployee() != null
                                ? cell.getValue().getUserIdEmployee()
                                : 0L
                ).asObject());
    }

    private void loadAll() {
        List<PurchaseTransaction> list = purchaseTransactionService.getList(1, 100);
        table.getItems().setAll(list);
    }

    private void onSearch(ActionEvent event) {
        String fromText = from.getText().trim();
        String toText = to.getText().trim();

        if (fromText.isEmpty() && toText.isEmpty()) {
            loadAll();
            return;
        }

        try {
            Timestamp fromTs = null;
            Timestamp toTs = null;

            if (!fromText.isEmpty()) {
                fromTs = new Timestamp(df.parse(fromText).getTime());
            }
            if (!toText.isEmpty()) {
                long millis = df.parse(toText).getTime() + (24 * 60 * 60 * 1000L) - 1;
                toTs = new Timestamp(millis);
            }

            if (fromTs == null || toTs == null) {
                showError("Vui lòng nhập đủ From và To theo định dạng dd/MM/yyyy");
                return;
            }

            List<PurchaseTransaction> list = purchaseTransactionService.findByTime(fromTs, toTs);
            table.getItems().setAll(list);

        } catch (ParseException e) {
            showError("Ngày không đúng định dạng dd/MM/yyyy");
        }
    }

    private void onGetDetails(ActionEvent event) {
        PurchaseTransaction selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Hãy chọn một lượt nhập để xem chi tiết.");
            return;
        }

        openDetailsWindow(selected);
    }

    private void openDetailsWindow(PurchaseTransaction transaction) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/DetailPurchaseView.fxml"));
            Scene scene = new Scene(loader.load());

            DetailsPurchaseTransactionController controller = loader.getController();
            controller.setPurchaseTransaction(transaction);

            Stage stage = new Stage();
            stage.setTitle("Chi tiết lượt nhập #" + transaction.getId());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showError("Không thể mở màn hình chi tiết: " + e.getMessage());
        }
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        alert.showAndWait();
    }
}
