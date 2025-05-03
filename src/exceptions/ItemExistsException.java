package exceptions;

public class ItemExistsException extends Exception {

    public ItemExistsException(String message) {
        super(message);
    }

    public ItemExistsException() {
        super("Item already exists in the Menu");
    }
}
