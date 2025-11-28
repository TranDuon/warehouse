package com.mycompany.warehouse_desktop.controller.user;

import com.mycompany.warehouse_desktop.controller.Session;   // 🌟 ĐÃ THÊM
import com.mycompany.warehouse_desktop.db.roles_of_user.RolesOfUser;
import com.mycompany.warehouse_desktop.db.roles_of_user.RolesOfUserService;

import com.mycompany.warehouse_desktop.db.role.Role;
import com.mycompany.warehouse_desktop.db.role.RoleService;

import com.mycompany.warehouse_desktop.db.user.UserEntity;
import com.mycompany.warehouse_desktop.db.user.UserService;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class ListUserController {

    @FXML private TableView<UserEntity> table;
    @FXML private TableColumn<UserEntity, Number> colId;
    @FXML private TableColumn<UserEntity, String> colUsername;
    @FXML private TableColumn<UserEntity, String> colEmail;
    @FXML private TableColumn<UserEntity, String> colPhone;
    @FXML private TableColumn<UserEntity, Boolean> colEnabled;
    @FXML private TableColumn<UserEntity, String> colRoles;

    @FXML private Button btnCreate;
    @FXML private Button btnEdit;
    @FXML private Button btnDelete;

    private final UserService userService = new UserService();
    private final RolesOfUserService rolesOfUserService = new RolesOfUserService();
    private final RoleService roleService = new RoleService();

    @FXML
    private void initialize() {

        checkPermission();
        setupColumns();
        loadUsers();

        btnCreate.setOnAction(e -> openCreateUser());
        btnEdit.setOnAction(e -> editSelected());
        btnDelete.setOnAction(e -> deleteSelected());
    }

    //  PHÂN QUYỀN ADMIN
    private void checkPermission() {
        UserEntity currentUser = Session.get();
        if (currentUser == null) return;

        List<RolesOfUser> roles = rolesOfUserService.findByUserId(currentUser.getId());
        boolean isAdmin = roles.stream()
                .anyMatch(r -> r.getId().getRoleId() == 1);  // role ID 1 = ADMIN

        if (!isAdmin) {
            btnCreate.setDisable(true);
            btnEdit.setDisable(true);
            btnDelete.setDisable(true);

            new Alert(Alert.AlertType.ERROR,
                    "Bạn không có quyền truy cập vào mục quản lý người dùng!")
                    .show();
        }
    }
    //


    private void setupColumns() {
        colId.setCellValueFactory(c -> new SimpleLongProperty(c.getValue().getId()));
        colUsername.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getUsername()));
        colEmail.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEmail()));
        colPhone.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPhoneNumber()));
        colEnabled.setCellValueFactory(c -> new SimpleBooleanProperty(c.getValue().getEnabled()));

        colRoles.setCellValueFactory(c -> {
            List<RolesOfUser> mapping = rolesOfUserService.findByUserId(c.getValue().getId());

            String roles = mapping.stream()
                    .map(m -> roleService.findById(m.getId().getRoleId()))
                    .filter(r -> r != null)
                    .map(Role::getName)
                    .collect(Collectors.joining(", "));

            return new SimpleStringProperty(roles);
        });
    }

    private void loadUsers() {
        table.getItems().setAll(userService.getList(1, 200));
    }

    private void openCreateUser() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/User/CreateUserView.fxml"));
            Parent root = loader.load();

            Stage st = new Stage();
            st.setScene(new Scene(root));
            st.setTitle("Tạo tài khoản mới");
            st.showAndWait();

            loadUsers();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void editSelected() {
        UserEntity user = table.getSelectionModel().getSelectedItem();
        if (user == null) return;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/User/EditUserView.fxml"));
            Parent root = loader.load();

            EditUserController controller = loader.getController();
            controller.loadUser(user);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Chỉnh sửa tài khoản");
            stage.showAndWait();

            loadUsers();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteSelected() {
        UserEntity user = table.getSelectionModel().getSelectedItem();
        if (user == null) return;

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Bạn có chắc muốn xóa tài khoản này?",
                ButtonType.YES, ButtonType.NO);

        confirm.showAndWait();

        if (confirm.getResult() != ButtonType.YES) return;

        userService.delete(user.getId());

        List<RolesOfUser> list = rolesOfUserService.findByUserId(user.getId());
        for (RolesOfUser r : list) {
            rolesOfUserService.delete(r.getId());
        }

        loadUsers();
    }
}
