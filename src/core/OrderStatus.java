package core;

public enum OrderStatus {
    PENDING("Pending"),
    IN_PROGRESS("In Progress"),
    READY("Ready"),
    CANCELLED("Cancelled"),
    SERVED("Served");

    private final String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
