package com.example.oopproject.core;

import com.example.oopproject.exceptions.ItemExistsException;
import java.util.ArrayList;
import com.example.oopproject.utils.FileUtils;

/**
 * Manages a list of MenuItem objects representing the restaurant's menu.
 * Supports adding, removing, retrieving, saving, and loading menu items.
 */
public class MenuManager {

    private ArrayList<MenuItem> menuItems = new ArrayList<>();

    /**
     * Adds a new item to the menu.
     * Throws an exception if an identical item already exists in the menu.
     *
     * @param item the MenuItem to add
     * @throws ItemExistsException if an item with the same properties already exists
     */
    public void addItem(MenuItem item) throws ItemExistsException {
        for (MenuItem menuItem : menuItems) {
            if (menuItem.equals(item)) {
                throw new ItemExistsException("The item with the name " + item.getName() +
                        ", price " + item.getPrice() +
                        ", category " + item.getCategory() +
                        ", and description \"" + item.getDescription() + "\" already exists.");
            }
        }
        menuItems.add(item);
    }

    /**
     * Removes an item from the menu based on its unique ID.
     *
     * @param id the ID of the item to remove
     */
    public void removeItem(int id) {
        menuItems.removeIf(item -> item.getId() == id);
    }

    /**
     * Returns a deep copy of the current menu.
     * Modifying the returned list will not affect the original menu.
     *
     * @return a copy of the list of menu items
     */
    public ArrayList<MenuItem> getMenu() {
        ArrayList<MenuItem> copy = new ArrayList<>();
        for (MenuItem item : menuItems) {
            copy.add(new MenuItem(item));
        }
        return copy;
    }

    /**
     * Saves the current menu to a file named "menu.csv".
     * Each menu item is saved as a comma-separated line.
     */
    public void save() {
        ArrayList<String> lines = new ArrayList<>();
        for (MenuItem item : menuItems) {
            lines.add(item.getName() + "," +
                    item.getPrice() + "," +
                    item.getCategory() + "," +
                    item.getDescription());
        }
        FileUtils.writeLines(lines, "menu.csv");
    }

    /**
     * Loads the menu from the "menu.csv" file.
     * Clears the existing menu items before loading.
     * Ignores lines with incorrect format or invalid number formats.
     */
    public void load() {
        menuItems.clear();
        ArrayList<String> lines = FileUtils.readLines("menu.csv");

        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length == 4) {
                try {
                    String name = parts[0];
                    double price = Double.parseDouble(parts[1]);
                    String category = parts[2];
                    String description = parts[3];
                    menuItems.add(new MenuItem(name, price, category, description));
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing menu item: " + line);
                }
            }
        }
    }
}
