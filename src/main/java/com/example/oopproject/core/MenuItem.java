package com.example.oopproject.core;

/**
 * Represents an item on the restaurant's menu.
 * Each MenuItem has a unique ID, name, price, category, and description.
 */
public class MenuItem {

    private static int nextId = 0;

    private final int id;
    private String name;
    private double price;
    private String description;
    private String category;

    /**
     * Constructs a new MenuItem with the specified details.
     * The item is automatically assigned a unique ID.
     *
     * @param name        the name of the menu item
     * @param price       the price of the menu item
     * @param category    the category of the menu item (e.g., appetizer, main course, dessert)
     * @param description a brief description of the menu item
     */
    public MenuItem(String name, double price, String category, String description) {
        this.id = nextId++;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    /**
     * Creates a copy of the given MenuItem.
     *
     * @param item the MenuItem to copy
     */
    public MenuItem(MenuItem item) {
        this.id = item.id;
        this.name = item.name;
        this.description = item.description;
        this.price = item.price;
        this.category = item.category;
    }

    /**
     * Returns the unique ID of the menu item.
     *
     * @return the menu item's ID
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns the name of the menu item.
     *
     * @return the name of the item
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the price of the menu item.
     *
     * @return the price of the item
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * Returns the description of the menu item.
     *
     * @return a brief description of the item
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the category of the menu item.
     *
     * @return the category the item belongs to
     */
    public String getCategory() {
        return this.category;
    }

    /**
     * Compares this MenuItem to another object for equality.
     * Two MenuItems are considered equal if they have the same ID,
     * name, price, description, and category.
     *
     * @param obj the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MenuItem other = (MenuItem) obj;
        return id == other.id
                && Double.compare(other.price, price) == 0
                && name.equals(other.name)
                && description.equals(other.description)
                && category.equals(other.category);
    }
}
