package com.example.oopproject.ui;

import com.example.oopproject.core.MenuItem;
import com.example.oopproject.core.MenuManager;
import com.example.oopproject.exceptions.ItemExistsException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class ManageMenuItemsController {

    @FXML private TableView<MenuItem> menuTable;
    @FXML private TableColumn<MenuItem, String> nameColumn;
    @FXML private TableColumn<MenuItem, Double> priceColumn;
    @FXML private TableColumn<MenuItem, String> categoryColumn;
    @FXML private TableColumn<MenuItem, String> descriptionColumn;

    @FXML private TextField nameField;
    @FXML private TextField priceField;
    @FXML private TextField categoryField;
    @FXML private TextArea descriptionField;
    @FXML private Label messageLabel;

    private MenuManager menuManager = new MenuManager();
    private ObservableList<MenuItem> menuItems;

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        menuManager.load();

        menuItems = FXCollections.observableArrayList(menuManager.getMenu());
        menuTable.setItems(menuItems);

        // Add listener for table selection to populate form fields
        menuTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                nameField.setText(newSelection.getName());
                priceField.setText(String.valueOf(newSelection.getPrice()));
                categoryField.setText(newSelection.getCategory());
                descriptionField.setText(newSelection.getDescription());
            }
        });
    }

    @FXML
    private void handleAddItem() {
        try {
            String name = nameField.getText().trim();
            String priceText = priceField.getText().trim();
            String category = categoryField.getText().trim();
            String description = descriptionField.getText().trim();

            if (name.isEmpty() || priceText.isEmpty() || category.isEmpty()) {
                messageLabel.setText("Name, price, and category are required!");
                return;
            }

            double price;
            try {
                price = Double.parseDouble(priceText);
                if (price < 0) {
                    messageLabel.setText("Price cannot be negative!");
                    return;
                }
            } catch (NumberFormatException e) {
                messageLabel.setText("Price must be a valid number!");
                return;
            }

            MenuItem newItem = new MenuItem(name, price, category, description);
            menuManager.addItem(newItem);

            menuItems.clear();
            menuItems.addAll(menuManager.getMenu());

            menuManager.save();

            clearFields();
            messageLabel.setText("Item added successfully!");

        } catch (ItemExistsException e) {
            messageLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void handleRemoveItem() {
        MenuItem selectedItem = menuTable.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            messageLabel.setText("Please select an item to remove!");
            return;
        }

        menuManager.removeItem(selectedItem.getId());

        menuItems.clear();
        menuItems.addAll(menuManager.getMenu());

        menuManager.save();

        clearFields();
        messageLabel.setText("Item removed successfully!");
    }

    @FXML
    private void handleBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/oopproject/AdminDashboard.fxml"));
            Stage stage = (Stage) menuTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Error navigating back: " + e.getMessage());
        }
    }

    private void clearFields() {
        nameField.clear();
        priceField.clear();
        categoryField.clear();
        descriptionField.clear();
    }
}