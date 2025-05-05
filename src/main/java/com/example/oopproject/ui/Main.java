package com.example.oopproject.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.image.Image;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/oopproject/login.fxml"));

        Image icon = new Image(getClass().getResourceAsStream("/com/example/oopproject/app-icon.png"));
        primaryStage.getIcons().add(icon);

        primaryStage.setTitle("Restaurant Management System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
