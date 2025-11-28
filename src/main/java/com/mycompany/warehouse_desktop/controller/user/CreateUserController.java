package com.mycompany.warehouse_desktop.controller.user;

import com.mycompany.warehouse_desktop.db.role.Role;
import com.mycompany.warehouse_desktop.db.role.RoleService;
import com.mycompany.warehouse_desktop.db.roles_of_user.RolesOfUser;
import com.mycompany.warehouse_desktop.db.roles_of_user.RolesOfUserId;
import com.mycompany.warehouse_desktop.db.roles_of_user.RolesOfUserService;
import com.mycompany.warehouse_desktop.db.user.UserEntity;
import com.mycompany.warehouse_desktop.db.user.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class CreateUserController {

    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private TextField email;
    @FXML private TextField phone;
    @FXML private CheckBox enabled;
    @FXML private ListView<Role> roleList;
    @FXML private Button btnSave;
    @FXML private Button btnCancel;

    private final UserService userService = new UserService();
    private final RoleService roleService = new RoleService();
    private final RolesOfUserService rolesOfUserService = new RolesOfUserService();

    @FXML
    private void initialize() {
        loadRoles();
        setupRoleDisplay();

        btnSave.setOnAction(e -> save());
        btnCancel.setOnAction(e -> close());
    }


    private void setupRoleDisplay() {
        roleList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Role role, boolean empty) {
                super.updateItem(role, empty);
                if (empty || role == null) {
                    setText(null);
                } else {
                    setText(role.getName());
                }
            }
        });
    }

    private void loadRoles() {
        List<Role> roles = roleService.getList(1, 100);
        roleList.getItems().setAll(roles);
        roleList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }


    private void save() {
        if (username.getText().isBlank() || password.getText().isBlank()) {
            showAlert("Vui lòng nhập Username và Password!");
            return;
        }

        UserEntity user = new UserEntity(
                null,
                username.getText(),
                password.getText(),
                email.getText(),
                phone.getText(),
                enabled.isSelected()
        );

        UserEntity saved = userService.create(user);
        if (saved == null) {
            showAlert("Lỗi tạo tài khoản!");
            return;
        }

        // Lưu role cho user
        for (Role r : roleList.getSelectionModel().getSelectedItems()) {
            RolesOfUserId id = new RolesOfUserId(saved.getId(), r.getId());
            rolesOfUserService.create(new RolesOfUser(id));
        }

        new Alert(Alert.AlertType.INFORMATION, "Tạo tài khoản thành công!").show();
        close();
    }

    private void close() {
        Stage st = (Stage) btnCancel.getScene().getWindow();
        st.close();
    }

    private void showAlert(String msg) {
        new Alert(Alert.AlertType.WARNING, msg).show();
    }
}
