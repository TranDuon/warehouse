package com.mycompany.warehouse_desktop.controller.user;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class UserHomeController {

    @FXML private Button btnCreate;
    @FXML private Button btnList;

    @FXML
    public void initialize() {
        btnCreate.setOnAction(e -> openCreateUser());
        btnList.setOnAction(e -> openListUser());
    }

    private void openCreateUser() {
        openWindow("/view/User/CreateUserView.fxml", "Tạo tài khoản mới");
    }

    private void openListUser() {
        openWindow("/view/User/ListUserView.fxml", "Danh sách người dùng");
    }

    private void openWindow(String path, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();

            Stage st = new Stage();
            st.setTitle(title);
            st.setScene(new Scene(root));
            st.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
