package com.example.oopproject.ui;

import com.example.oopproject.core.*;
import com.example.oopproject.core.MenuItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PlaceOrderController {

    @FXML private TableView<MenuItem> menuTable;
    @FXML private TableColumn<MenuItem, String> nameColumn;
    @FXML private TableColumn<MenuItem, Double> priceColumn;
    @FXML private TableColumn<MenuItem, String> categoryColumn;

    @FXML private TableView<OrderItem> orderTable;
    @FXML private TableColumn<OrderItem, String> itemNameColumn;
    @FXML private TableColumn<OrderItem, Integer> quantityColumn;
    @FXML private TableColumn<OrderItem, Double> priceColumnOrder;

    @FXML private TextField quantityField;
    @FXML private TextField tableNumberField;
    @FXML private Label totalLabel;

    private final MenuManager menuManager = RestaurantUI.getMenuManager();
    private final OrderManager orderManager = RestaurantUI.getOrderManager();
    private final Map<Integer, Integer> currentOrder = new HashMap<>(); // menuItemId -> quantity

    @FXML
    public void initialize() {
        setupMenuTable();
        setupOrderTable();
        loadMenu();
    }

    private void setupMenuTable() {
        nameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));
        priceColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        categoryColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCategory()));
    }

    private void setupOrderTable() {
        itemNameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getMenuItem().getName()));
        quantityColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        priceColumnOrder.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getTotalPrice()).asObject());
    }

    private void loadMenu() {
        menuTable.setItems(FXCollections.observableArrayList(menuManager.getMenu()));
    }

    @FXML
    private void addToOrder() {
        MenuItem selected = menuTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Error", "No item selected");
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityField.getText());
            if (quantity <= 0) {
                showAlert("Error", "Quantity must be positive");
                return;
            }

            currentOrder.merge(selected.getId(), quantity, Integer::sum);
            updateOrderDisplay();
            quantityField.clear();

        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid quantity");
        }
    }

    @FXML
    private void removeFromOrder() {
        OrderItem selected = orderTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Error", "No item selected");
            return;
        }

        currentOrder.remove(selected.getMenuItem().getId());
        updateOrderDisplay();
    }

    @FXML
    private void placeOrder() {
        try {
            int tableNumber = Integer.parseInt(tableNumberField.getText());

            if (currentOrder.isEmpty()) {
                showAlert("Error", "Order is empty");
                return;
            }

            Order newOrder = new Order(tableNumber);
            for (Map.Entry<Integer, Integer> entry : currentOrder.entrySet()) {
                for (MenuItem item : menuManager.getMenu()) {
                    if (item.getId() == entry.getKey()) {
                        newOrder.addItem(new OrderItem(item, entry.getValue()));
                        break;
                    }
                }
            }

            orderManager.addOrder(newOrder);
            orderManager.save();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Order Placed");
            alert.setHeaderText("Order #" + newOrder.getId());
            alert.setContentText(getOrderSummary(newOrder));
            alert.showAndWait();

            currentOrder.clear();
            updateOrderDisplay();
            tableNumberField.clear();

        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid table number");
        } catch (Exception e) {
            showAlert("Error", "Failed to save order: " + e.getMessage());
        }
    }

    private String getOrderSummary(Order order) {
        StringBuilder sb = new StringBuilder();
        sb.append("Table: ").append(order.getTableNumber()).append("\n");

        for (OrderItem item : order.getItems()) {
            sb.append(String.format("- %s x%d: $%.2f\n",
                    item.getMenuItem().getName(),
                    item.getQuantity(),
                    item.getTotalPrice()));
        }

        sb.append(String.format("Total: $%.2f", order.getTotalPrice()));
        return sb.toString();
    }

    private void updateOrderDisplay() {
        ObservableList<OrderItem> orderItems = FXCollections.observableArrayList();
        double total = 0;

        for (Map.Entry<Integer, Integer> entry : currentOrder.entrySet()) {
            for (MenuItem item : menuManager.getMenu()) {
                if (item.getId() == entry.getKey()) {
                    OrderItem orderItem = new OrderItem(item, entry.getValue());
                    orderItems.add(orderItem);
                    total += orderItem.getTotalPrice();
                    break;
                }
            }
        }

        orderTable.setItems(orderItems);
        totalLabel.setText(String.format("Total: $%.2f", total));
    }

    @FXML
    private void handleBack() {
        try {
            orderManager.save();

            Stage stage = (Stage) menuTable.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/oopproject/CustomerDashboard.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Customer Dashboard");
        } catch (IOException e) {
            showAlert("Error", "Failed to return to dashboard");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
