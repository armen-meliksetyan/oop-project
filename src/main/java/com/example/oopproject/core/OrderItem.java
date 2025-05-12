package com.example.oopproject.core;

/**
 * Represents an order item that combines a {@link MenuItem} and a quantity.
 * This class implements the {@link Orderable} interface, allowing it to be
 * part of an order and providing the details of the ordered item and its total price.
 */
public class OrderItem implements Orderable {

    private static int nextId = 0;
    private final int id;
    private MenuItem menuItem;
    private int quantity;

    /**
     * Constructs an {@link OrderItem} with the specified menu item and quantity.
     *
     * @param menuItem the menu item for the order
     * @param quantity the quantity of the menu item being ordered
     */
    public OrderItem(MenuItem menuItem, int quantity) {
        this.id = nextId++;
        this.menuItem = menuItem;
        this.quantity = quantity;
    }

    /**
     * Returns a copy of the menu item associated with this order item.
     *
     * @return a new {@link MenuItem} object that represents the ordered item
     */
    public MenuItem getMenuItem() {
        return new MenuItem(menuItem);
    }

    /**
     * Sets the quantity of the ordered item.
     *
     * @param quantity the new quantity for the ordered item
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Returns the quantity of the ordered item.
     *
     * @return the quantity of the ordered item
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Returns the total price for the ordered item, calculated by multiplying
     * the price of the menu item by the quantity ordered.
     *
     * @return the total price of the order item
     */
    public double getTotalPrice() {
        return menuItem.getPrice() * quantity;
    }

    /**
     * Returns the unique ID of this order item.
     *
     * @return the ID of the order item
     */
    public int getId() {
        return id;
    }
}
