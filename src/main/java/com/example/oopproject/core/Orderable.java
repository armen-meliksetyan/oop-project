package com.example.oopproject.core;

/**
 * This interface defines the common behavior for objects that can be ordered,
 * such as order items in a menu or an order.
 * Classes implementing this interface should provide details about the menu item,
 * its quantity, and the total price of the ordered item(s).
 */
public interface Orderable {

    /**
     * Returns the menu item associated with the orderable object.
     *
     * @return the menu item being ordered
     */
    MenuItem getMenuItem();

    /**
     * Returns the quantity of the menu item being ordered.
     *
     * @return the quantity of the menu item
     */
    int getQuantity();

    /**
     * Returns the total price of the ordered menu item(s), which is the product
     * of the menu item's price and the quantity.
     *
     * @return the total price of the ordered item(s)
     */
    double getTotalPrice();
}
