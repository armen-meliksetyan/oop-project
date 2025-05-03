package core;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Order {

    private static int nextId = 0;

    private final int id;
    private ArrayList<OrderItem> items;
    private int tableNumber;
    private OrderStatus status;

    public Order(int tableNumber) {
        this.id = nextId++;
        this.tableNumber = tableNumber;
        this.status = OrderStatus.PENDING;
        this.items = new ArrayList<>();
    }

    public Order(int tableNumber, String status) {
        this.id = nextId++;
        this.tableNumber = tableNumber;
        this.status = OrderStatus.PENDING;
        this.items = new ArrayList<>();
    }

    public void addItem(OrderItem orderItem) {
        items.add(orderItem);
    }

    public void removeItem(int orderItemId) throws NoSuchElementException {
        boolean removed = items.removeIf(item -> item.getId() == orderItemId);
        if (!removed) {
            throw new NoSuchElementException("OrderItem with ID " + orderItemId + " not found in order");
        }
    }

    public double getTotalPrice() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public ArrayList<OrderItem> getItems() {
        return items;
    }
}
