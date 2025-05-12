package com.example.oopproject.cli;

import com.example.oopproject.core.*;
import com.example.oopproject.exceptions.ItemExistsException;
import java.util.ArrayList;
import java.util.Scanner;
import com.example.oopproject.utils.PasswordUtils;
/**
 * CLI-based interface for interacting with the Restaurant Management System.
 * Provides functionality for both admin and customer roles including:
 * authentication, registration, order management, and menu operations.
 */

public class RestaurantCLI {

    private static Scanner scanner = new Scanner(System.in);
    private static MenuManager menuManager = new MenuManager();
    private static OrderManager orderManager = new OrderManager();
    private static User currentUser = null;
    private static UserManager userManager = new UserManager();

    /**
     * Entry point of the application.
     * Loads data, shows login screen, and dispatches to appropriate menu.
     */
    public static void main(String[] args) {
        menuManager.load();
        orderManager.load(menuManager);
        menuManager.load();
        orderManager.load(menuManager);

        loginScreen();

        if (currentUser != null) {
            if (currentUser.getRole().equals("ADMIN")) {
                adminMenu();
            } else {
                customerMenu();
            }
        }

        // Save data before exiting
        menuManager.save();
        orderManager.save();
    }

    /**
     * Displays the login screen and handles user authentication and registration routing.
     */
    private static void loginScreen() {
        System.out.println("=== Restaurant Management System ===");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Choose option: ");

        int choice = Integer.parseInt(scanner.nextLine());

        if (choice == 1) {
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();

            currentUser = userManager.authenticate(username, password);

            if (currentUser != null) {
                System.out.println("Logged in as " + currentUser.getRole());
                if (currentUser.getRole().equals("ADMIN")) {
                    adminMenu();
                } else {
                    customerMenu();
                }
            } else {
                System.out.println("Invalid credentials!");
                loginScreen();
            }
        } else if (choice == 2) {
            registerScreen();
        } else {
            System.exit(0);
        }
    }
    /**
     * Displays registration options and handles role-specific registration.
     */
    private static void registerScreen() {
        System.out.println("\n=== REGISTER ===");
        System.out.println("1. Register as Admin (requires admin code)");
        System.out.println("2. Register as Customer");
        System.out.println("3. Back to Login");
        System.out.print("Choose option: ");

        int choice = Integer.parseInt(scanner.nextLine());

        if (choice == 1) {
            System.out.print("Enter admin registration code: ");
            String code = scanner.nextLine();
            if (!code.equals("ADMIN123")) { // Simple security, replace with better method
                System.out.println("Invalid admin code!");
                registerScreen();
                return;
            }
            registerUser(true);
        } else if (choice == 2) {
            registerUser(false);
        } else {
            loginScreen();
        }
    }

    /**
     * Handles the creation and storage of a new user (admin or customer).
     * @param isAdmin true if registering an admin, false for a customer.
     */
    private static void registerUser(boolean isAdmin) {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User newUser;
        if (isAdmin) {
            newUser = new Admin(username, email, PasswordUtils.hash(password));
        } else {
            newUser = new Customer(username, email, PasswordUtils.hash(password));
        }

        userManager.addUser(newUser);
        System.out.println("Registration successful! Please login.");
        loginScreen();
    }

    /**
     * Displays the admin menu and dispatches to corresponding admin actions.
     */
    private static void adminMenu() {
        while (true) {
            System.out.println("\n=== ADMIN MENU ===");
            System.out.println("1. Manage Menu Items");
            System.out.println("2. Manage Orders");
            System.out.println("3. View All Orders");
            System.out.println("4. Logout");
            System.out.print("Choose option: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    manageMenuItems();
                    break;
                case 2:
                    manageOrders();
                    break;
                case 3:
                    viewAllOrders();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    /**
     * Displays the customer menu and dispatches to customer actions.
     */
    private static void customerMenu() {
        while (true) {
            System.out.println("\n=== CUSTOMER MENU ===");
            System.out.println("1. View Menu");
            System.out.println("2. Place Order");
            System.out.println("3. View My Orders");
            System.out.println("4. Logout");
            System.out.print("Choose option: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    viewMenu();
                    break;
                case 2:
                    placeOrder();
                    break;
                case 3:
                    viewCustomerOrders();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    /**
     * Displays and handles menu item management (add, remove, view).
     */
    private static void manageMenuItems() {
        while (true) {
            System.out.println("\n=== MANAGE MENU ITEMS ===");
            System.out.println("1. Add Menu Item");
            System.out.println("2. Remove Menu Item");
            System.out.println("3. View Menu");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choose option: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addMenuItem();
                    break;
                case 2:
                    removeMenuItem();
                    break;
                case 3:
                    viewMenu();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    /**
     * Prompts the admin to add a new item to the menu.
     */
    private static void addMenuItem() {
        System.out.println("\n=== ADD MENU ITEM ===");
        System.out.print("Item Name: ");
        String name = scanner.nextLine();
        System.out.print("Price: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Category: ");
        String category = scanner.nextLine();
        System.out.print("Description: ");
        String description = scanner.nextLine();

        try {
            menuManager.addItem(new MenuItem(name, price, category, description));
            System.out.println("Menu item added successfully!");
        } catch (ItemExistsException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    /**
     * Prompts the admin to remove a menu item by ID.
     */
    private static void removeMenuItem() {
        System.out.println("\n=== REMOVE MENU ITEM ===");
        viewMenu();
        System.out.print("Enter ID of item to remove: ");
        int id = Integer.parseInt(scanner.nextLine());

        menuManager.removeItem(id);
        System.out.println("Menu item removed successfully!");
    }

    /**
     * Prints all current menu items to the console.
     */
    private static void viewMenu() {
        System.out.println("\n=== MENU ===");
        ArrayList<MenuItem> menu = menuManager.getMenu();
        if (menu.isEmpty()) {
            System.out.println("No items in the menu.");
            return;
        }

        for (MenuItem item : menu) {
            System.out.printf("[ID: %d] %s - $%.2f\n", item.getId(), item.getName(), item.getPrice());
            System.out.printf("Category: %s | Description: %s\n\n", item.getCategory(), item.getDescription());
        }
    }

    /**
     * Displays options to view or update orders for admin.
     */
    private static void manageOrders() {
        while (true) {
            System.out.println("\n=== MANAGE ORDERS ===");
            System.out.println("1. View Pending Orders");
            System.out.println("2. Update Order Status");
            System.out.println("3. Back to Main Menu");
            System.out.print("Choose option: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    viewOrdersByStatus(OrderStatus.PENDING);
                    break;
                case 2:
                    updateOrderStatus();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }


    /**
     * Prints all orders, regardless of status or owner.
     */
    private static void viewAllOrders() {
        System.out.println("\n=== ALL ORDERS ===");
        for (Order order : orderManager.getOrders()) {
            printOrderDetails(order);
        }
    }

    /**
     * Prints orders filtered by a specific status (e.g. PENDING, COMPLETED).
     * @param status The order status to filter by.
     */
    private static void viewOrdersByStatus(OrderStatus status) {
        System.out.println("\n=== " + status.toString().toUpperCase() + " ORDERS ===");
        boolean found = false;

        for (Order order : orderManager.getOrders()) {
            if (order.getStatus() == status) {
                printOrderDetails(order);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No " + status.toString().toLowerCase() + " orders.");
        }
    }

    /**
     * Prints detailed information for a specific order.
     * @param order The order to display.
     */
    private static void printOrderDetails(Order order) {
        System.out.printf("\n[Order ID: %d] Table: %d | Status: %s\n",
                order.getId(), order.getTableNumber(), order.getStatus());

        for (OrderItem item : order.getItems()) {
            System.out.printf("- %s x%d: $%.2f\n",
                    item.getMenuItem().getName(),
                    item.getQuantity(),
                    item.getTotalPrice());
        }

        System.out.printf("Total: $%.2f\n", order.getTotalPrice());
    }

    /**
     * Allows admin to update the status of an existing order.
     */
    private static void updateOrderStatus() {
        viewAllOrders();
        System.out.print("\nEnter Order ID to update: ");
        int orderId = Integer.parseInt(scanner.nextLine());

        Order orderToUpdate = null;
        for (Order order : orderManager.getOrders()) {
            if (order.getId() == orderId) {
                orderToUpdate = order;
                break;
            }
        }

        if (orderToUpdate == null) {
            System.out.println("Order not found!");
            return;
        }

        System.out.println("\nCurrent status: " + orderToUpdate.getStatus());
        System.out.println("Select new status:");
        for (OrderStatus status : OrderStatus.values()) {
            System.out.println(status.ordinal() + 1 + ". " + status);
        }
        System.out.print("Choose status: ");

        int statusChoice = Integer.parseInt(scanner.nextLine());
        if (statusChoice < 1 || statusChoice > OrderStatus.values().length) {
            System.out.println("Invalid status choice!");
            return;
        }

        OrderStatus newStatus = OrderStatus.values()[statusChoice - 1];
        orderToUpdate.setStatus(newStatus);
        System.out.println("Order status updated to " + newStatus);
    }

    /**
     * Allows customers to place a new order by selecting menu items.
     */
    private static void placeOrder() {
        System.out.println("\n=== PLACE NEW ORDER ===");
        viewMenu();

        System.out.print("Enter table number: ");
        int tableNumber = Integer.parseInt(scanner.nextLine());

        Order newOrder = new Order(tableNumber);
        boolean addingItems = true;

        while (addingItems) {
            System.out.print("Enter menu item ID to add (or 0 to finish): ");
            int itemId = Integer.parseInt(scanner.nextLine());

            if (itemId == 0) {
                addingItems = false;
                continue;
            }

            MenuItem selectedItem = null;
            for (MenuItem item : menuManager.getMenu()) {
                if (item.getId() == itemId) {
                    selectedItem = item;
                    break;
                }
            }

            if (selectedItem == null) {
                System.out.println("Invalid menu item ID!");
                continue;
            }

            System.out.print("Enter quantity: ");
            int quantity = Integer.parseInt(scanner.nextLine());

            newOrder.addItem(new OrderItem(selectedItem, quantity));
            System.out.printf("Added %d x %s to order\n", quantity, selectedItem.getName());
        }

        if (!newOrder.getItems().isEmpty()) {
            orderManager.addOrder(newOrder);
            System.out.println("\nOrder placed successfully!");
            printOrderDetails(newOrder);
        } else {
            System.out.println("Order canceled - no items added.");
        }
    }

    /**
     * Displays all orders placed by the customer.
     * (Currently shows all orders; should be filtered by user in production).
     */
    private static void viewCustomerOrders() {
        System.out.println("\n=== YOUR ORDERS ===");
        boolean found = false;

        // In a real system, you'd filter by current user's orders
        // For demo, we'll show all orders
        for (Order order : orderManager.getOrders()) {
            printOrderDetails(order);
            found = true;
        }

        if (!found) {
            System.out.println("No orders found.");
        }
    }

}
