package com.example.oopproject.core;

/**
 * Enum representing the different statuses that an order can have in the system.
 * Each status has a corresponding display name that is used for representing it as a string.
 */
public enum OrderStatus {

    /**
     * The order has been placed but not yet processed.
     */
    PENDING("Pending"),

    /**
     * The order is currently being processed or prepared.
     */
    IN_PROGRESS("In Progress"),

    /**
     * The order is ready to be served.
     */
    READY("Ready"),

    /**
     * The order has been cancelled and will not be processed further.
     */
    CANCELLED("Cancelled"),

    /**
     * The order has been served to the customer.
     */
    SERVED("Served");

    private final String displayName;

    /**
     * Constructs an OrderStatus with a specific display name.
     *
     * @param displayName the display name of the order status
     */
    OrderStatus(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the display name of the order status.
     *
     * @return the display name of the order status
     */
    @Override
    public String toString() {
        return displayName;
    }
}
