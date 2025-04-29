package core;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utils.FileUtils;

public class OrderManager {
    private List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        orders.add(order);
    }

    public List<Order> getOrders() {
        return orders;
    }

    // public void createOrder(int tableNumber) {
    //     Order order = new Order(tableNumber);
    //     orders.add(order);
    // }

    public void save() {
    List<String> lines = new ArrayList<>();
    for (Order order : orders) {
        for (OrderItem item : order.getItems()) {
            lines.add(order.getId() + "," +
                      order.getTableNumber() + "," +
                      item.getMenuItem().getId() + "," +
                      item.getQuantity() + "," +
                      order.getStatus());
        }
    }
    FileUtils.writeLines(lines, "orders.txt");
}

public void load(MenuManager menuManager) {
    orders.clear();
    List<String> lines = FileUtils.readLines("orders.txt");
    for (String line : lines) {
        String[] parts = line.split(",");
        if (parts.length != 5) continue;

        int orderId = Integer.parseInt(parts[0]);
        int tableNumber = Integer.parseInt(parts[1]);
        int menuItemId = Integer.parseInt(parts[2]);
        int quantity = Integer.parseInt(parts[3]);
        String status = parts[4];

        Order order = findOrCreateOrder(orderId, tableNumber, status);
        MenuItem menuItem = findMenuItemById(menuManager.getMenu(), menuItemId);
        if (menuItem != null) {
            order.addItem(new OrderItem(menuItem, quantity));
        }
    }
}

private Order findOrCreateOrder(int orderId, int tableNumber, String status) {
    for (Order order : orders) {
        if (order.getId() == orderId) {
            return order;
        }
    }
    Order newOrder = new Order(orderId, tableNumber, status);
    orders.add(newOrder);
    return newOrder;
}

private MenuItem findMenuItemById(List<MenuItem> menu, int id) {
    for (MenuItem item : menu) {
        if (item.getId() == id) {
            return item;
        }
    }
    return null;
}


}
