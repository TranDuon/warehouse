package com.mycompany.warehouse_desktop.controller.user;

import com.mycompany.warehouse_desktop.controller.Session;
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
import java.util.stream.Collectors;

public class EditUserController {

    @FXML private TextField id;
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

    private UserEntity currentUser;

    @FXML
    private void initialize() {
        roleList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        setupRoleDisplay();

        checkPermission();

        btnSave.setOnAction(e -> save());
        btnCancel.setOnAction(e -> close());
    }

    private void checkPermission() {
        UserEntity logged = Session.get();
        if (logged == null) return;

        List<RolesOfUser> roles = rolesOfUserService.findByUserId(logged.getId());

        boolean isAdmin = roles.stream()
                .anyMatch(r -> r.getId().getRoleId() == 1L); // role 1 = admin

        if (!isAdmin) {
            btnSave.setDisable(true);
            roleList.setDisable(true);
            username.setEditable(false);
            password.setEditable(false);
            email.setEditable(false);
            phone.setEditable(false);
            enabled.setDisable(true);

            new Alert(Alert.AlertType.ERROR,
                    "Bạn không có quyền chỉnh sửa tài khoản người dùng!")
                    .show();
        }
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

    public void loadUser(UserEntity user) {
        this.currentUser = user;

        id.setText(String.valueOf(user.getId()));
        username.setText(user.getUsername());
        password.setText(user.getPassword());
        email.setText(user.getEmail());
        phone.setText(user.getPhoneNumber());
        enabled.setSelected(user.getEnabled());

        loadRoles();
        loadCheckedRoles();
    }

    private void loadRoles() {
        List<Role> roles = roleService.getList(1, 100);
        roleList.getItems().setAll(roles);
    }

    private void loadCheckedRoles() {
        List<RolesOfUser> list = rolesOfUserService.findByUserId(currentUser.getId());

        List<Long> userRoleIds = list.stream()
                .map(r -> r.getId().getRoleId())
                .collect(Collectors.toList());

        for (Role r : roleList.getItems()) {
            if (userRoleIds.contains(r.getId())) {
                roleList.getSelectionModel().select(r);
            }
        }
    }

    private void save() {
        if (username.getText().isBlank()) {
            showAlert("Username không được để trống!");
            return;
        }

        UserEntity updated = new UserEntity(
                currentUser.getId(),
                username.getText(),
                password.getText(),
                email.getText(),
                phone.getText(),
                enabled.isSelected()
        );

        userService.update(updated.getId(), updated);

        List<RolesOfUser> oldRoles = rolesOfUserService.findByUserId(updated.getId());
        for (RolesOfUser r : oldRoles) {
            rolesOfUserService.delete(r.getId());
        }

        for (Role r : roleList.getSelectionModel().getSelectedItems()) {
            RolesOfUserId rid = new RolesOfUserId(updated.getId(), r.getId());
            rolesOfUserService.create(new RolesOfUser(rid));
        }

        new Alert(Alert.AlertType.INFORMATION, "Cập nhật thành công!").show();
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
