package com.example.oopproject.ui;

import com.example.oopproject.core.*;
import com.example.oopproject.utils.PasswordUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {

    @FXML private RadioButton customerRadio;
    @FXML private RadioButton adminRadio;
    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private TextField adminCodeField;
    @FXML private Label messageLabel;

    private final UserManager userManager = new UserManager();
    private ToggleGroup userTypeGroup;  // Properly declared as instance variable

    @FXML
    public void initialize() {
        // Initialize the toggle group
        userTypeGroup = new ToggleGroup();
        customerRadio.setToggleGroup(userTypeGroup);
        adminRadio.setToggleGroup(userTypeGroup);
        customerRadio.setSelected(true);
        customerRadio.setUserData("CUSTOMER");
        adminRadio.setUserData("ADMIN");

        // Bind admin code field visibility
        adminCodeField.visibleProperty().bind(adminRadio.selectedProperty());
    }

    @FXML
    private void handleRegister() {
        // Get the selected user type
        RadioButton selectedRadio = (RadioButton)userTypeGroup.getSelectedToggle();
        String userType = (String)selectedRadio.getUserData();

        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();

        // Validation (unchanged from your original logic)
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showError("Please fill in all fields!");
            return;
        }

        if ("ADMIN".equals(userType) && !"ADMIN123".equals(adminCodeField.getText().trim())) {
            showError("Invalid admin code!");
            return;
        }

        try {
            User newUser = "ADMIN".equals(userType)
                    ? new Admin(username, email, PasswordUtils.hash(password))
                    : new Customer(username, email, PasswordUtils.hash(password));

            userManager.addUser(newUser);
            showSuccess("Registration successful!");

            // Return to login after delay
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            javafx.application.Platform.runLater(() -> handleBackToLogin());
                        }
                    },
                    1500
            );

        } catch (Exception e) {
            showError("Registration failed: " + e.getMessage());
        }
    }

    @FXML
    private void handleBackToLogin() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/oopproject/login.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
        } catch (IOException e) {
            showError("Failed to load login page");
        }
    }

    private void showError(String message) {
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-text-fill: red;");
    }

    private void showSuccess(String message) {
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-text-fill: green;");
    }
}