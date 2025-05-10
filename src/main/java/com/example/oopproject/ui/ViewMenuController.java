package com.example.oopproject.ui;

import com.example.oopproject.core.MenuItem;
import com.example.oopproject.core.MenuManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class ViewMenuController {

    @FXML
    private ListView<String> menuListView;

    private MenuManager menuManager = new MenuManager();

    @FXML
    public void initialize() {
        menuManager.load();
        displayMenuItems();
    }

    private void displayMenuItems() {
        ObservableList<String> items = FXCollections.observableArrayList();

        // Add header
        items.add(String.format("%-25s %-10s %-15s %s",
                "Name", "Price", "Category", "Description"));
        items.add("------------------------------------------------------------");

        for (MenuItem item : menuManager.getMenu()) {
            String display = String.format("%-25s $%-9.2f %-15s %s",
                    item.getName(),
                    item.getPrice(),
                    item.getCategory(),
                    item.getDescription());
            items.add(display);
        }

        if (menuManager.getMenu().isEmpty()) {
            items.add("No items in the menu.");
        }

        menuListView.setItems(items);
    }

    @FXML
    private void handleBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/oopproject/CustomerDashboard.fxml"));
            Stage stage = (Stage) menuListView.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Customer Dashboard");
        } catch (IOException e) {
            showError("Error loading Customer Dashboard.");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}