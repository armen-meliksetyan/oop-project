package com.example.oopproject.core;

/**
 * Represents a customer user in the Restaurant Management System.
 * A customer can place orders and view their order history.
 * Inherits common user properties from the {@link User} class.
 */
public final class Customer extends User {

    /**
     * Constructs a new Customer with the given credentials.
     *
     * @param username     the customer's username
     * @param email        the customer's email address
     * @param passwordHash the hashed password of the customer
     */
    public Customer(String username, String email, String passwordHash) {
        super(username, email, passwordHash);
        // this.orderHistory = new ArrayList<>();
    }

    /**
     * Returns the role of this user.
     *
     * @return the string "CUSTOMER"
     */
    @Override
    public String getRole() {
        return "CUSTOMER";
    }
}
