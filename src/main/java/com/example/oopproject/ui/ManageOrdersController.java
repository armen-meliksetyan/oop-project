package com.example.oopproject.ui;

import com.example.oopproject.core.*;
import com.example.oopproject.core.MenuItem;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.List;

public class ManageOrdersController {

    @FXML private TableView<Order> ordersTable;
    @FXML private TableColumn<Order, Integer> orderIdColumn;
    @FXML private TableColumn<Order, Integer> tableNumberColumn;
    @FXML private TableColumn<Order, String> statusColumn;
    @FXML private TableColumn<Order, Double> totalColumn;

    @FXML private TableView<OrderItem> itemsTable;
    @FXML private TableColumn<OrderItem, String> itemNameColumn;
    @FXML private TableColumn<OrderItem, Integer> quantityColumn;
    @FXML private TableColumn<OrderItem, Double> itemTotalColumn;

    @FXML private ComboBox<OrderStatus> statusComboBox;

    private OrderManager orderManager;
    private MenuManager menuManager;

    @FXML
    public void initialize() {
        this.orderManager = RestaurantUI.getOrderManager();
        this.menuManager = RestaurantUI.getMenuManager();

        orderIdColumn.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getId()).asObject());
        tableNumberColumn.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getTableNumber()).asObject());
        statusColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getStatus().toString()));
        totalColumn.setCellValueFactory(cell -> new SimpleDoubleProperty(cell.getValue().getTotalPrice()).asObject());

        itemNameColumn.setCellValueFactory(cell -> {
            MenuItem menuItem = cell.getValue().getMenuItem();
            return new SimpleStringProperty(menuItem != null ? menuItem.getName() : "N/A");
        });
        quantityColumn.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getQuantity()).asObject());
        itemTotalColumn.setCellValueFactory(cell -> new SimpleDoubleProperty(cell.getValue().getTotalPrice()).asObject());

        statusComboBox.setItems(FXCollections.observableArrayList(OrderStatus.values()));
        statusComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(OrderStatus status) {
                return status.toString();
            }
            @Override
            public OrderStatus fromString(String string) {
                return OrderStatus.valueOf(string);
            }
        });

        // Add selection listener to show order items
        ordersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldOrder, newOrder) -> {
            if (newOrder != null) {
                updateOrderDetails(newOrder);
            } else {
                itemsTable.getItems().clear();
            }
        });

        loadOrders();
    }

    private void updateOrderDetails(Order order) {
        ObservableList<OrderItem> items = FXCollections.observableArrayList(order.getItems());
        itemsTable.setItems(items);
        statusComboBox.setValue(order.getStatus());
    }

    private void loadOrders() {
        if (orderManager != null) {
            List<Order> orders = orderManager.getOrders();
            ObservableList<Order> observableOrders = FXCollections.observableArrayList(orders);
            ordersTable.setItems(observableOrders);

            if (!orders.isEmpty()) {
                ordersTable.getSelectionModel().selectFirst();
            }
        }
    }

    @FXML
    private void handleRefresh() {
        loadOrders();
    }

    @FXML
    private void handleViewPending() {
        if (orderManager != null) {
            List<Order> pendingOrders = orderManager.getOrders().stream()
                    .filter(order -> order.getStatus() == OrderStatus.PENDING)
                    .toList();
            ordersTable.setItems(FXCollections.observableArrayList(pendingOrders));
        }
    }

    @FXML
    private void handleViewAll() {
        loadOrders();
    }

    @FXML
    private void handleUpdateStatus() {
        Order selectedOrder = ordersTable.getSelectionModel().getSelectedItem();
        if (selectedOrder == null) {
            showAlert("Error", "No order selected");
            return;
        }

        OrderStatus newStatus = statusComboBox.getValue();
        if (newStatus == null) {
            showAlert("Error", "No status selected");
            return;
        }

        selectedOrder.setStatus(newStatus);

        ordersTable.refresh();

        try {
            orderManager.save();
            showAlert("Success", "Order status updated to " + newStatus);
        } catch (Exception e) {
            showAlert("Error", "Failed to save changes: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oopproject/AdminDashboard.fxml"));
            Parent root = loader.load();

            AdminDashboardController controller = loader.getController();
            controller.setOrderManager(orderManager);
            controller.setMenuManager(menuManager);

            Stage stage = (Stage) ordersTable.getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
            stage.setTitle("Admin Dashboard");
        } catch (Exception e) {
            showAlert("Navigation Error", "Failed to return to dashboard: " + e.getMessage());
        }
    }
}
