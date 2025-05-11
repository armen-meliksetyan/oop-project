package com.example.oopproject.core;

import java.util.ArrayList;
import com.example.oopproject.utils.FileUtils;

public class OrderManager {

    private ArrayList<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        orders.add(order);
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void load(MenuManager menuManager) {
        orders.clear();
        ArrayList<String> lines = FileUtils.readLines("orders.csv");
    
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length != 4) {
                continue;
            }
    
            int tableNumber = Integer.parseInt(parts[0]);
            int menuItemId = Integer.parseInt(parts[1]);
            int quantity = Integer.parseInt(parts[2]);
            
            // Convert display name to enum
            OrderStatus status = parseOrderStatus(parts[3].trim());
    
            Order order = findOrCreateOrder(tableNumber, status);
            MenuItem menuItem = findMenuItemById(menuManager.getMenu(), menuItemId);
            if (menuItem != null) {
                order.addItem(new OrderItem(menuItem, quantity));
            }
        }
    }
    
    private OrderStatus parseOrderStatus(String displayName) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.toString().equalsIgnoreCase(displayName)) {
                return status;
            }
        }
        return OrderStatus.PENDING; // default if not found
    }

    public void save() {
        ArrayList<String> lines = new ArrayList<>();
        for (Order order : orders) {
            for (OrderItem item : order.getItems()) {
                lines.add(order.getTableNumber() + ","
                        + item.getMenuItem().getId() + ","
                        + item.getQuantity() + ","
                        + order.getStatus().toString()); // Explicit toString()
            }
        }
        FileUtils.writeLines(lines, "orders.csv");
    }

    private Order findOrCreateOrder(int tableNumber, OrderStatus status) {
        for (Order order : orders) {
            if (order.getTableNumber() == tableNumber && order.getStatus() == status) {
                return order;
            }
        }

        Order newOrder = new Order(tableNumber);
        newOrder.setStatus(status);
        orders.add(newOrder);
        return newOrder;
    }

    private MenuItem findMenuItemById(ArrayList<MenuItem> menu, int id) {
        for (MenuItem item : menu) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }
}
