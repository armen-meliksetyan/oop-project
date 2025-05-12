package com.example.oopproject;

import com.example.oopproject.cli.RestaurantCLI;
import com.example.oopproject.ui.RestaurantUI;

/**
 * The main entry point for the OOP project application.
 * <p>
 * This class allows the application to be started with either a
 * command-line interface (CLI) or a graphical user interface (UI),
 * depending on the argument provided.
 * </p>
 * <p>
 * Usage:
 * <ul>
 *     <li><code>-cli</code> to launch the CLI</li>
 *     <li><code>-ui</code> to launch the GUI</li>
 *     <li>No argument defaults to GUI</li>
 * </ul>
 * </p>
 */
public class Main {

    /**
     * The application's main method. Determines whether to launch the CLI or UI
     * based on the provided command-line arguments.
     *
     * @param args Command-line arguments:
     *             <ul>
     *                 <li><code>-cli</code> to launch the CLI</li>
     *                 <li><code>-ui</code> to launch the GUI</li>
     *                 <li>Default is GUI if no arguments are provided</li>
     *             </ul>
     */
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

    /**
     * Prints usage instructions for launching the application.
     */
    private static void printUsage() {
        System.out.println("Usage: java -jar your-app.jar [option]");
        System.out.println("Options:");
        System.out.println("  -ui    Launch graphical user interface");
        System.out.println("  -cli   Launch command-line interface");
    }
}
