package com.example.oopproject.core;

import java.util.ArrayList;
import com.example.oopproject.utils.FileUtils;

/**
 * Manages orders in the system. Handles adding, retrieving, saving, and loading orders
 * from a file, as well as associating order items with corresponding menu items.
 */
public class OrderManager {

    private ArrayList<Order> orders = new ArrayList<>();

    /**
     * Adds a new order to the order manager.
     *
     * @param order the {@link Order} to be added
     */
    public void addOrder(Order order) {
        orders.add(order);
    }

    /**
     * Retrieves the list of all orders in the system.
     *
     * @return a list of {@link Order} objects
     */
    public ArrayList<Order> getOrders() {
        return orders;
    }

    /**
     * Loads orders from the "orders.csv" file and populates the order list.
     * It also ensures that the corresponding menu items are linked to the orders.
     *
     * @param menuManager the {@link MenuManager} to retrieve menu items from
     */
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

    /**
     * Parses the string representation of an order status and converts it to the
     * corresponding {@link OrderStatus} enum.
     *
     * @param displayName the string representation of an order status
     * @return the corresponding {@link OrderStatus} enum
     */
    private OrderStatus parseOrderStatus(String displayName) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.toString().equalsIgnoreCase(displayName)) {
                return status;
            }
        }
        return OrderStatus.PENDING; // default if not found
    }

    /**
     * Saves all current orders to the "orders.csv" file.
     */
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

    /**
     * Finds an existing order or creates a new one based on the table number and status.
     *
     * @param tableNumber the table number of the order
     * @param status the status of the order
     * @return the existing or newly created {@link Order}
     */
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

    /**
     * Finds a menu item by its ID from the provided menu.
     *
     * @param menu the list of {@link MenuItem} objects
     * @param id the ID of the menu item to be found
     * @return the {@link MenuItem} object, or null if not found
     */
    private MenuItem findMenuItemById(ArrayList<MenuItem> menu, int id) {
        for (MenuItem item : menu) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }
}
