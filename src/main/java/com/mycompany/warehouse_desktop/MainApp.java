package com.mycompany.warehouse_desktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import com.mycompany.warehouse_desktop.controller.LoginController;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/view/Login/Login.fxml")
        );

        Parent root = loader.load();

        // lấy controller
        LoginController controller = loader.getController();
        controller.init(stage);

        stage.setTitle("Warehouse Management");
        stage.setScene(new Scene(root));
        stage.show();
    }

    //xác nhận thoát app
    public void LogOut(Stage stage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Log out");
        alert.setHeaderText("You're about to log out.");
        alert.setContentText("Do you want to exit?");

        if(alert.showAndWait().get() == ButtonType.OK){
            System.out.println("Logged out successfully!");
            stage.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
