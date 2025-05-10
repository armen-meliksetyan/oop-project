package com.example.oopproject.ui;

import com.example.oopproject.core.Order;
import com.example.oopproject.core.OrderItem;
import com.example.oopproject.core.OrderManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewMyOrdersController implements Initializable {

    @FXML private ListView<Order> ordersListView;
    @FXML private TextArea orderDetailsTextArea;
    @FXML private Label titleLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        titleLabel.setText("All Orders");

        // Get the OrderManager instance from Main
        OrderManager orderManager = Main.getOrderManager();

        // Configure ListView to display orders properly
        ordersListView.setCellFactory(param -> new ListCell<Order>() {
            @Override
            protected void updateItem(Order order, boolean empty) {
                super.updateItem(order, empty);
                if (empty || order == null) {
                    setText(null);
                } else {
                    setText(String.format("Order #%d - Table %d - %s",
                            order.getId(),
                            order.getTableNumber(),
                            order.getStatus()));
                }
            }
        });

        // Load all orders (like CLI version)
        ordersListView.getItems().addAll(orderManager.getOrders());

        // Set up listener for order selection
        ordersListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showOrderDetails(newValue));
    }

    private void showOrderDetails(Order order) {
        if (order == null) {
            orderDetailsTextArea.clear();
            return;
        }

        StringBuilder details = new StringBuilder();
        details.append(String.format("Order ID: %d\n", order.getId()));
        details.append(String.format("Table: %d\n", order.getTableNumber()));
        details.append(String.format("Status: %s\n\n", order.getStatus()));

        for (OrderItem item : order.getItems()) {
            details.append(String.format("- %s x%d: $%.2f\n",
                    item.getMenuItem().getName(),
                    item.getQuantity(),
                    item.getTotalPrice()));
        }

        details.append(String.format("\nTotal: $%.2f", order.getTotalPrice()));
        orderDetailsTextArea.setText(details.toString());
    }

    @FXML
    private void handleBack() throws IOException {
        // Load the Customer Dashboard
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/oopproject/CustomerDashboard.fxml"));
        Stage stage = (Stage) ordersListView.getScene().getWindow();
        stage.setScene(new Scene(root, 800, 600));
        stage.setTitle("Customer Dashboard");
    }
}