package com.example.oopproject.ui;

import com.example.oopproject.core.MenuManager;
import com.example.oopproject.core.Order;
import com.example.oopproject.core.OrderManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.image.Image;

/**
 * Main entry point for the Restaurant Management System user interface.
 * <p>
 * This class is responsible for initializing and launching the JavaFX application,
 * loading the main UI from an FXML file, and setting up the application window
 * with an icon and title.
 * </p>
 */
public class RestaurantUI extends Application {

    private static MenuManager menuManager;
    private static OrderManager orderManager;

    /**
     * Initializes the user interface and sets up the main stage.
     * <p>
     * Loads the menu and order data from files and prepares the initial UI scene.
     * </p>
     *
     * @param primaryStage the primary stage for this application
     * @throws Exception if loading the FXML file or setting up the scene fails
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialize managers
        menuManager = new MenuManager();
        orderManager = new OrderManager();

        menuManager.load();
        orderManager.load(menuManager);
        menuManager.load();
        orderManager.load(menuManager);

        // Load the main login screen from FXML
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/oopproject/login.fxml"));

        // Set the application icon
        Image icon = new Image(getClass().getResourceAsStream("/com/example/oopproject/app-icon.png"));
        primaryStage.getIcons().add(icon);

        // Set the title and scene for the primary stage
        primaryStage.setTitle("Restaurant Management System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * Gets the instance of the {@link MenuManager} for managing menu items.
     *
     * @return the MenuManager instance
     */
    public static MenuManager getMenuManager() {
        return menuManager;
    }

    /**
     * Gets the instance of the {@link OrderManager} for managing orders.
     *
     * @return the OrderManager instance
     */
    public static OrderManager getOrderManager() {
        return orderManager;
    }

    /**
     * The main entry point for launching the JavaFX application.
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(String[] args) {
        launch(args);
    }
}
