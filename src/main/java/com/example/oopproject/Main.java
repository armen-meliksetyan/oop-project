package com.example.oopproject;

import com.example.oopproject.cli.RestaurantCLI;
import com.example.oopproject.ui.RestaurantUI;

public class Main {
    public static void main(String[] args) {
        if (args.length > 0) {
            if (args[0].equals("-cli")) {
                RestaurantCLI.main(args);
            } else if (args[0].equals("-ui")) {
                RestaurantUI.main(args);
            } else {
                printUsage();
            }
        } else {
            // Default to UI if no arguments provided
            RestaurantUI.main(args);
        }
    }

    private static void printUsage() {
        System.out.println("Usage: java -jar your-app.jar [option]");
        System.out.println("Options:");
        System.out.println("  -ui    Launch graphical user interface");
        System.out.println("  -cli   Launch command-line interface");
    }
}
