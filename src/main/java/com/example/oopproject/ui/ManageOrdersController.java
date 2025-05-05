package com.example.oopproject.ui;

import com.example.oopproject.core.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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
    @FXML private Button updateStatusButton;
    @FXML private Button refreshButton;

    private OrderManager orderManager;
    private MenuManager menuManager;

    public void setOrderManager(OrderManager orderManager) {
        this.orderManager = orderManager;
        initializeTable();
    }

    public void setMenuManager(MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    @FXML
    public void initialize() {
        // Initialize columns for orders table
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableNumberColumn.setCellValueFactory(new PropertyValueFactory<>("tableNumber"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        // Initialize columns for items table
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("menuItemName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        itemTotalColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        // Initialize status combo box
        statusComboBox.setItems(FXCollections.observableArrayList(OrderStatus.values()));

        // Set up selection listener for orders table
        ordersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                showOrderDetails(newSelection);
                statusComboBox.setValue(newSelection.getStatus());
            }
        });
    }

    private void initializeTable() {
        if (orderManager != null) {
            List<Order> orders = orderManager.getOrders();
            ObservableList<Order> observableOrders = FXCollections.observableArrayList(orders);
            ordersTable.setItems(observableOrders);
        }
    }

    private void showOrderDetails(Order order) {
        ObservableList<OrderItem> items = FXCollections.observableArrayList();
        for (OrderItem item : order.getItems()) {
            items.add(item);
        }
        itemsTable.setItems(items);
    }

    @FXML
    private void handleUpdateStatus() {
        Order selectedOrder = ordersTable.getSelectionModel().getSelectedItem();
        if (selectedOrder == null) {
            showAlert("No Order Selected", "Please select an order to update.");
            return;
        }

        OrderStatus newStatus = statusComboBox.getValue();
        if (newStatus == null) {
            showAlert("No Status Selected", "Please select a status.");
            return;
        }

        selectedOrder.setStatus(newStatus);
        ordersTable.refresh();
        showAlert("Status Updated", "Order status updated to " + newStatus);
    }

    @FXML
    private void handleRefresh() {
        initializeTable();
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
        if (orderManager != null) {
            ordersTable.setItems(FXCollections.observableArrayList(orderManager.getOrders()));
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}