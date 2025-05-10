package com.example.oopproject.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerDashboardController {

    @FXML private Label welcomeLabel;

    @FXML
    public void initialize() {
        // Set welcome message
        welcomeLabel.setText("Welcome, " + LoginController.getCurrentUser().getUsername() + "!");
    }

    @FXML
    private void handleViewMenu() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/oopproject/ViewMenu.fxml"));
            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
            stage.setTitle("Menu View");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handlePlaceOrder() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/oopproject/PlaceOrder.fxml"));
            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
            stage.setTitle("Place Order");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleViewMyOrders() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/oopproject/ViewMyOrders.fxml"));
            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
            stage.setTitle("My Orders");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExit() {
        Stage stage = (Stage) welcomeLabel.getScene().getWindow();
        stage.close();
    }


}