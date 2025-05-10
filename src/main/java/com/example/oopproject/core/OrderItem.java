package com.example.oopproject.core;

public class OrderItem implements Orderable{

    private static int nextId = 0;
    private final int id;
    private MenuItem menuItem;
    private int quantity;

    public OrderItem(MenuItem menuItem, int quantity) {
        this.id = nextId++;
        this.menuItem = menuItem;
        this.quantity = quantity;
    }

    public MenuItem getMenuItem() {
        return new MenuItem(menuItem);
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return menuItem.getPrice() * quantity;
    }

    public int getId() {
        return id;
    }
}
