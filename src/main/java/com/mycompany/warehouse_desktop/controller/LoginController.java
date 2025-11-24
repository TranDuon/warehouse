package com.mycompany.warehouse_desktop.controller;

import com.mycompany.warehouse_desktop.db.user.UserDto;
import com.mycompany.warehouse_desktop.db.user.UserEntity;
import com.mycompany.warehouse_desktop.db.user.UserService;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML private TextField username;
    @FXML private PasswordField userpassword;
    @FXML private Button login_button;
    @FXML private Label signup;
    @FXML private Label noti;

    private Stage stage;
    private UserService userService = new UserService();

    public void init(Stage stage) {
        this.stage = stage;

        noti.setVisible(false);

        login_button.setOnAction(e -> loginAction());
        signup.setOnMouseClicked(e -> showSignup());
    }

    @FXML
    private void loginAction() {
        String usernameVal = username.getText();
        String passwordVal = userpassword.getText();

        UserDto dto = new UserDto(usernameVal, passwordVal);
        UserEntity user = userService.findByUsernamePassword(dto);

        if (user != null) {
            System.out.println("Login thành công: " + user.getUsername());

            // TODO: Load Home.fxml (sau này)
            // loadHome();
        } else {
            noti.setText("Incorrect username or password!");
            noti.setVisible(true);
        }

        clearForm();
    }

    private void clearForm() {
        username.clear();
        userpassword.clear();
    }

    private void showSignup() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/signup.fxml")
            );
            Parent root = loader.load();

            SignupController controller = loader.getController();
            controller.init(stage, this);

            stage.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void backToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/Login.fxml")
            );
            Parent root = loader.load();

            LoginController controller = loader.getController();
            controller.init(stage);

            stage.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
