package com.mycompany.warehouse_desktop.controller;

import com.mycompany.warehouse_desktop.db.user.UserEntity;
import com.mycompany.warehouse_desktop.db.user.UserService;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class SignupController {

    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private TextField email;
    @FXML private TextField email1;

    @FXML private Button create;
    @FXML private Label login;

    private Stage stage;
    private LoginController loginController;

    private UserService userService = new UserService();

    public void init(Stage stage, LoginController loginController){
        this.stage = stage;
        this.loginController = loginController;

        create.setOnAction(e -> registerAction());
        login.setOnMouseClicked(e -> backToLogin());
    }

    private void registerAction(){
        String usernameVal = username.getText();
        String passwordVal = password.getText();
        String emailVal = email.getText();
        String phoneVal = email1.getText();

        if(usernameVal.isEmpty() || passwordVal.isEmpty() ||
                emailVal.isEmpty() || phoneVal.isEmpty()){
            return;
        }

        UserEntity newUser = new UserEntity(
                null,
                usernameVal,
                passwordVal,
                emailVal,
                phoneVal,
                true
        );

        userService.create(newUser);

        clearForm();
        backToLogin();
    }

    private void clearForm(){
        username.clear();
        password.clear();
        email.clear();
        email1.clear();
    }

    private void backToLogin(){
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/Login/Login.fxml")
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
