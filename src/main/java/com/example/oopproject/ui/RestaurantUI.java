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

public class RestaurantUI extends Application {

    private static MenuManager menuManager;
    private static OrderManager orderManager;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialize managers
        menuManager = new MenuManager();
        orderManager = new OrderManager();

        menuManager.load();
        orderManager.load(menuManager);
        menuManager.load();
        orderManager.load(menuManager);
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/oopproject/login.fxml"));

        Image icon = new Image(getClass().getResourceAsStream("/com/example/oopproject/app-icon.png"));
        primaryStage.getIcons().add(icon);

        primaryStage.setTitle("Restaurant Management System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static MenuManager getMenuManager() {
        return menuManager;
    }

    public static OrderManager getOrderManager() {
        return orderManager;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
