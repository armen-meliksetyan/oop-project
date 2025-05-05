package com.example.oopproject.ui;

import com.example.oopproject.core.*;
import com.example.oopproject.utils.PasswordUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Modality;

public class RegisterController {

    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private TextField adminCodeField;
    @FXML private Label messageLabel;
    @FXML private ToggleGroup userTypeGroup;

    private UserManager userManager = new UserManager();
    private Stage primaryStage;  // Reference to the primary stage

    // Method to set the primary stage (called from LoginController)
    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    @FXML
    public void initialize() {
        // Show/hide admin code field based on user type selection
        userTypeGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                boolean isAdmin = "ADMIN".equals(newValue.getUserData().toString());
                adminCodeField.setVisible(isAdmin);
                if (isAdmin) {
                    adminCodeField.clear();
                }
            }
        });
    }

    @FXML
    private void handleRegister() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String userType = userTypeGroup.getSelectedToggle().getUserData().toString();

        // Validate inputs
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showErrorMessage("Please fill in all fields!");
            return;
        }

        // Email validation
        if (!email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            showErrorMessage("Please enter a valid email address!");
            return;
        }

        // Password strength validation
        if (password.length() < 6) {
            showErrorMessage("Password must be at least 6 characters long!");
            return;
        }

        // Admin registration validation
        if ("ADMIN".equals(userType)) {
            String adminCode = adminCodeField.getText().trim();
            if (!adminCode.equals("ADMIN123")) {
                showErrorMessage("Invalid admin code!");
                return;
            }
        }

        // Check if username already exists
        if (userManager.authenticate(username, password) != null) {
            showErrorMessage("Username already exists!");
            return;
        }

        try {
            // Create and add user
            User newUser;
            if ("ADMIN".equals(userType)) {
                newUser = new Admin(username, email, PasswordUtils.hash(password));
            } else {
                newUser = new Customer(username, email, PasswordUtils.hash(password));
            }

            userManager.addUser(newUser);
            showSuccessMessage("Registration successful! Please login.");

            // Close the registration window and return to login
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            showErrorMessage("Registration failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackToLogin() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/oopproject/login.fxml"));

            Stage currentStage = (Stage) usernameField.getScene().getWindow();

            Scene loginScene = new Scene(root);
            currentStage.setScene(loginScene);


        } catch (Exception e) {
            messageLabel.setText("Error loading login page");
            messageLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    private void showErrorMessage(String message) {
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-text-fill: red;");
    }

    private void showSuccessMessage(String message) {
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-text-fill: green;");
    }
}