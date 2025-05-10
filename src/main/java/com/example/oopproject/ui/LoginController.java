package com.example.oopproject.ui;

import com.example.oopproject.core.User;
import com.example.oopproject.core.UserManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    private UserManager userManager = new UserManager();

    @FXML
    public void initialize() {
        // Load existing users if stored (optional)
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        User user = userManager.authenticate(username, password);

        if (user != null) {
            currentUser = user;
            messageLabel.setText("Login successful as " + user.getRole());
            openDashboard(user);
        } else {
            messageLabel.setText("Invalid credentials!");
        }
    }

    @FXML
    private void handleRegister() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/oopproject/register.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openDashboard(User user) {
        try {
            String fxmlFile = user.getRole().equals("ADMIN") ? "AdminDashboard.fxml" : "CustomerDashboard.fxml";
            String fullPath = "/com/example/oopproject/" + fxmlFile;

            if (getClass().getResource(fullPath) == null) {
                throw new IOException("FXML file not found: " + fullPath);
            }

            Parent root = FXMLLoader.load(getClass().getResource(fullPath));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
            stage.setTitle(user.getRole().equals("ADMIN") ? "Admin Dashboard" : "Customer Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Error loading dashboard: " + e.getMessage());
        }
    }
    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

}