package com.example.oopproject.core;

import java.util.ArrayList;
import java.util.List;
import com.example.oopproject.utils.FileUtils;
import com.example.oopproject.utils.PasswordUtils;

/**
 * Manages user authentication, creation, and storage in the system.
 * Responsible for loading, saving, and authenticating users (Admin and Customer).
 */
public class UserManager {

    private static final String USERS_FILE = "users.csv";
    private ArrayList<User> users = new ArrayList<>();

    /**
     * Constructs a UserManager and loads users from the file. If no users exist, a default admin is added.
     */
    public UserManager() {
        loadUsers();
        // Add default admin if no users exist
        if (users.isEmpty()) {
            users.add(new Admin("admin", "admin@restaurant.com",
                    PasswordUtils.hash("admin123")));
            saveUsers();
        }
    }

    /**
     * Authenticates a user by verifying their username and password.
     *
     * @param username the username to authenticate
     * @param password the password to authenticate
     * @return the authenticated user if valid, or null if authentication fails
     */
    public User authenticate(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username)
                    && PasswordUtils.verify(password, user.getPasswordHash())) {
                return user;
            }
        }
        return null;
    }

    /**
     * Adds a new user to the system and saves the updated user list.
     *
     * @param user the user to add
     */
    public void addUser(User user) {
        users.add(user);
        saveUsers();
    }

    /**
     * Loads users from the stored file into memory.
     * Users are created based on their role (ADMIN or CUSTOMER).
     */
    private void loadUsers() {
        List<String> lines = FileUtils.readLines(USERS_FILE);
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length == 4) {
                String role = parts[0];
                String username = parts[1];
                String email = parts[2];
                String passwordHash = parts[3];

                if (role.equals("ADMIN")) {
                    users.add(new Admin(username, email, passwordHash));
                } else {
                    users.add(new Customer(username, email, passwordHash));
                }
            }
        }
    }

    /**
     * Saves the current list of users to the users file.
     * The file contains the user's role, username, email, and password hash.
     */
    private void saveUsers() {
        List<String> lines = new ArrayList<>();
        for (User user : users) {
            lines.add(user.getRole() + ","
                    + user.getUsername() + ","
                    + user.getEmail() + ","
                    + user.getPasswordHash());
        }
        FileUtils.writeLines(lines, USERS_FILE);
    }
}
