package com.example.oopproject.exceptions;

/**
 * Exception thrown when attempting to add an item that already exists in the menu.
 */
public class ItemExistsException extends Exception {

    /**
     * Constructs a new ItemExistsException with the specified detail message.
     *
     * @param message the detail message
     */
    public ItemExistsException(String message) {
        super(message);
    }

    /**
     * Constructs a new ItemExistsException with a default detail message.
     */
    public ItemExistsException() {
        super("Item already exists in the Menu");
    }
}
