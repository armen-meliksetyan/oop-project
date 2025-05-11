package com.example.oopproject.ui;

import com.example.oopproject.core.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewAllOrdersController implements Initializable {

    @FXML private TableView<Order> ordersTable;
    @FXML private TableColumn<Order, Integer> orderIdColumn;
    @FXML private TableColumn<Order, Integer> tableNumberColumn;
    @FXML private TableColumn<Order, String> statusColumn;
    @FXML private TableColumn<Order, Double> totalColumn;

    @FXML private TextArea orderDetailsArea;

    private OrderManager orderManager = RestaurantUI.getOrderManager(); // Assuming this is how you get the order manager

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize columns in the table
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableNumberColumn.setCellValueFactory(new PropertyValueFactory<>("tableNumber"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        // Set up selection listener for the table
        ordersTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> showOrderDetails(newSelection));

        // Refresh the table to show current orders
        refreshOrdersTable();
    }

    // This method will be called to refresh the table and display orders
    private void refreshOrdersTable() {
        if (orderManager != null) {
            // Check if there are any orders and print the count for debugging
            System.out.println("Loaded orders count: " + orderManager.getOrders().size());

            // Set items in the table
            ordersTable.setItems(FXCollections.observableArrayList(orderManager.getOrders()));
        }
    }

    // Show order details when an order is selected in the table
    private void showOrderDetails(Order order) {
        if (order == null) {
            orderDetailsArea.setText("No order selected");
            return;
        }

        // Format the details for the selected order
        StringBuilder details = new StringBuilder();
        details.append(String.format("[Order ID: %d] Table: %d | Status: %s\n\n",
                order.getId(), order.getTableNumber(), order.getStatus()));

        // List order items
        for (OrderItem item : order.getItems()) {
            details.append(String.format("- %s x%d: $%.2f\n",
                    item.getMenuItem().getName(),
                    item.getQuantity(),
                    item.getTotalPrice()));
        }

        // Append the total price of the order
        details.append(String.format("\nTotal: $%.2f", order.getTotalPrice()));
        orderDetailsArea.setText(details.toString());
    }

    // Method to handle the back button click and return to the Admin Dashboard
    @FXML
    private void handleBack() {
        try {
            Stage stage = (Stage) ordersTable.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/oopproject/AdminDashboard.fxml"));
            stage.setScene(new Scene(root, 800, 600));
            stage.setTitle("Admin Dashboard");
        } catch (Exception e) {
            showAlert("Error", "Failed to return to dashboard");
        }
    }

    // Helper method to show an alert box in case of an error
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
