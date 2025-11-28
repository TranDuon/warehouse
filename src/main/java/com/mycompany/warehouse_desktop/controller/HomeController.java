package com.mycompany.warehouse_desktop.controller;

import com.mycompany.warehouse_desktop.db.roles_of_user.RolesOfUser;
import com.mycompany.warehouse_desktop.db.roles_of_user.RolesOfUserService;
import com.mycompany.warehouse_desktop.db.user.UserEntity;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.util.List;

public class HomeController {

    @FXML
    private VBox mainContent;

    @FXML
    private Button userButton;   //

    private final RolesOfUserService rolesService = new RolesOfUserService();

    @FXML
    private void initialize() {
        applyPermission();
    }

    private void applyPermission() {
        UserEntity u = Session.get();
        if (u == null) return;

        List<RolesOfUser> roles = rolesService.findByUserId(u.getId());

        boolean isAdmin = roles.stream()
                .anyMatch(r -> r.getId().getRoleId() == 1L); //

        if (!isAdmin) {
            if (userButton != null) {
                userButton.setVisible(false);
                userButton.setDisable(true);
            }
        }
    }

    //  Chỉ admin mới được mở trang User
    public void openUserPage() {
        if (!isAdmin()) {
            System.out.println("Bạn không có quyền truy cập User Management.");
            return;
        }
        loadPage("/view/User/UserHomeView.fxml");
    }

    // Logic kiểm tra admin
    private boolean isAdmin() {
        UserEntity u = Session.get();
        if (u == null) return false;

        List<RolesOfUser> roles = rolesService.findByUserId(u.getId());

        return roles.stream()
                .anyMatch(r -> r.getId().getRoleId() == 1L);
    }

    public void openProductPage() {
        loadPage("/view/Product/ProductHomeView.fxml");
    }

    public void openPurchasePage() {
        loadPage("/view/Purchase/PurchaseHomeView.fxml");
    }

    public void openSellPage() {
        loadPage("/view/Sell/SellHomeView.fxml");
    }

    public void openHomePage() {
        loadPage("/view/Home/HomeView.fxml");
    }

    private void loadPage(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node content = loader.load();

            mainContent.getChildren().clear();
            mainContent.getChildren().add(content);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Cannot load FXML: " + fxmlPath);
        }
    }

}
