package com.example.oopproject.core;

/**
 * Abstract class representing a user in the system.
 * Contains common properties and methods for all types of users, such as Customer, Admin, etc.
 */
public abstract class User {

    private static int nextId = 0;
    private final int id;
    private final String username;
    private final String email;
    private final String passwordHash;

    /**
     * Constructs a new User with the specified username, email, and password hash.
     *
     * @param username the username of the user
     * @param email the email address of the user
     * @param passwordHash the hashed password of the user
     */
    public User(String username, String email, String passwordHash) {
        this.id = nextId++;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    /**
     * Returns the unique ID of the user.
     *
     * @return the user ID
     */
    public int getUserId() {
        return id;
    }

    /**
     * Returns the username of the user.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the email address of the user.
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Abstract method to return the role of the user. Implemented by subclasses to provide specific roles.
     *
     * @return the role of the user
     */
    public abstract String getRole();

    /**
     * Returns the hashed password of the user.
     *
     * @return the password hash
     */
    public String getPasswordHash() {
        return passwordHash;
    }
}
