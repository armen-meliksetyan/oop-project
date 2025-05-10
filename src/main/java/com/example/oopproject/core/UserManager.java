package com.example.oopproject.core;

import java.util.ArrayList;
import java.util.List;
import com.example.oopproject.utils.FileUtils;
import com.example.oopproject.utils.PasswordUtils;

public class UserManager {

    private static final String USERS_FILE = "users.csv";
    private ArrayList<User> users = new ArrayList<>();

    public UserManager() {
        loadUsers();
        // Add default admin if no users exist
        if (users.isEmpty()) {
            users.add(new Admin("admin", "admin@restaurant.com",
                    PasswordUtils.hash("admin123")));
            saveUsers();
        }
    }

    public User authenticate(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username)
                    && PasswordUtils.verify(password, user.getPasswordHash())) {
                return user;
            }
        }
        return null;
    }

    public void addUser(User user) {
        users.add(user);
        saveUsers();
    }

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
