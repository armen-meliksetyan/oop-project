package com.example.oopproject.ui;

import com.example.oopproject.core.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminDashboardController {

    @FXML private Label welcomeLabel;

    private OrderManager orderManager;
    private MenuManager menuManager;

    // Initialize managers when the controller is created
    public AdminDashboardController() {
        this.menuManager = new MenuManager();
        this.orderManager = new OrderManager();

        // Load data immediately
        this.menuManager.load();
        this.orderManager.load(this.menuManager);
    }

    @FXML
    public void initialize() {
        welcomeLabel.setText("Welcome Admin, " + LoginController.getCurrentUser().getUsername() + "!");
    }

    @FXML
    private void handleManageMenuItems() {
        loadScene("/com/example/oopproject/ManageMenuItems.fxml", "Manage Menu Items");
    }

    @FXML
    private void handleManageOrders() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oopproject/ManageOrders.fxml"));
            Parent root = loader.load();

            ManageOrdersController controller = loader.getController();

            // Reload data to ensure it's fresh
            menuManager.load();
            orderManager.load(menuManager);


            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
            stage.setTitle("Manage Orders");
        } catch (IOException e) {
            showError("Failed to load Manage Orders", e.getMessage());
        }
    }

    @FXML
    private void handleViewAllOrders() {
        loadScene("/com/example/oopproject/ViewAllOrders.fxml", "All Orders");
    }

    @FXML
    private void handleLogout() {
        loadScene("/com/example/oopproject/login.fxml", "Login");
    }

    private void loadScene(String fxmlPath, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
            stage.setTitle(title);
        } catch (IOException e) {
            showError("Navigation Error", "Failed to load " + title + ": " + e.getMessage());
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setOrderManager(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    public void setMenuManager(MenuManager menuManager) {
        this.menuManager = menuManager;
    }
}