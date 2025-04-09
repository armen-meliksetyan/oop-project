import java.util.ArrayList;
import java.util.List;

public class Order {
    private int id;
    private List<OrderItem> items = new ArrayList<>();
    private int tableNumber;
    private String status;

    public Order(int id, int tableNumber) {
        this.id = id;
        this.tableNumber = tableNumber;
        this.status = "Pending";
    }

    public void addItem(OrderItem orderItem) {
        items.add(orderItem);
    }

    public void removeItem(int orderItemId) {
        if (orderItemId >= 0 && orderItemId < items.size()) {
            items.remove(orderItemId);
        }
    }

    public double getTotalPrice() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public String getStatus() {
        return status;
    }

    public List<OrderItem> getItems() {
        return items;
    }
}
