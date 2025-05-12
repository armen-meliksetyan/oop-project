package com.example.oopproject.core;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Represents a customer's order at a restaurant, containing multiple order items,
 * the table number, status of the order, and a unique identifier.
 */
public class Order {

    private static int nextId = 0;

    private final int id;
    private ArrayList<OrderItem> items;
    private int tableNumber;
    private OrderStatus status;

    /**
     * Creates a new order with the specified table number.
     * The order starts with a status of PENDING and an empty list of items.
     *
     * @param tableNumber the table number placing the order
     */
    public Order(int tableNumber) {
        this.id = nextId++;
        this.tableNumber = tableNumber;
        this.status = OrderStatus.PENDING;
        this.items = new ArrayList<>();
    }

    /**
     * Creates a new order with the specified table number and a string status.
     * The status is not currently used and is defaulted to PENDING.
     * (Possibly intended for future parsing from persistent storage.)
     *
     * @param tableNumber the table number placing the order
     * @param status a string representation of the order status (currently unused)
     */
    public Order(int tableNumber, String status) {
        this.id = nextId++;
        this.tableNumber = tableNumber;
        this.status = OrderStatus.PENDING;
        this.items = new ArrayList<>();
    }

    /**
     * Adds an item to the order.
     *
     * @param orderItem the item to add
     */
    public void addItem(OrderItem orderItem) {
        items.add(orderItem);
    }

    /**
     * Removes an item from the order by its ID.
     *
     * @param orderItemId the ID of the item to remove
     * @throws NoSuchElementException if no item with the given ID is found
     */
    public void removeItem(int orderItemId) throws NoSuchElementException {
        boolean removed = items.removeIf(item -> item.getId() == orderItemId);
        if (!removed) {
            throw new NoSuchElementException("OrderItem with ID " + orderItemId + " not found in order");
        }
    }

    /**
     * Calculates and returns the total price of all items in the order.
     *
     * @return the total price of the order
     */
    public double getTotalPrice() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }

    /**
     * Sets the current status of the order.
     *
     * @param status the new status to set
     */
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    /**
     * Returns the unique ID of this order.
     *
     * @return the order ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the table number associated with the order.
     *
     * @return the table number
     */
    public int getTableNumber() {
        return tableNumber;
    }

    /**
     * Returns the current status of the order.
     *
     * @return the order status
     */
    public OrderStatus getStatus() {
        return status;
    }

    /**
     * Returns the list of order items in this order.
     *
     * @return a list of OrderItem objects
     */
    public ArrayList<OrderItem> getItems() {
        return items;
    }
}
