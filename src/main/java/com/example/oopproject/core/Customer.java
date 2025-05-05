package com.example.oopproject.core;

public final class Customer extends User {

    public Customer(String username, String email, String passwordHash) {
        super(username, email, passwordHash);
        // this.orderHistory = new ArrayList<>();
    }

    @Override
    public String getRole() {
        return "CUSTOMER";
    }
}
