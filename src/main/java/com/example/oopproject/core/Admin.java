package com.example.oopproject.core;

/**
 * Represents an administrator user in the restaurant management system.
 * This class extends the {@link User} class and provides specific functionality
 * for administrative users.
 * <p>
 * Admin users typically have elevated privileges compared to regular users.
 * This class is marked as {@code final} to prevent further inheritance.
 */
public final class Admin extends User {

    /**
     * Constructs a new Admin with the specified credentials.
     *
     * @param username     the unique identifier for the admin user
     * @param email        the email address associated with the admin account
     * @param passwordHash the hashed password for secure authentication
     */
    public Admin(String username, String email, String passwordHash) {
        super(username, email, passwordHash);
    }

    /**
     * Returns the role of this user, which is always "ADMIN" for instances of this class.
     *
     * @return a string constant "ADMIN" representing the administrative role
     */
    @Override
    public String getRole() {
        return "ADMIN";
    }

}