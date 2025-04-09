import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    private List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        orders.add(order);
    }

    public List<Order> getOrders() {
        return orders;
    }

    // public void createOrder(int tableNumber) {
    //     Order order = new Order(tableNumber);
    //     orders.add(order);
    // }

    public void saveOrders() {
        try (FileWriter writer = new FileWriter("orders.csv")) {
            for (Order order : orders) {
                for (OrderItem item : order.getItems()) {
                    writer.write(order.getId() + "," +
                                 order.getTableNumber() + "," +
                                 item.getMenuItem().getId() + "," +
                                 item.getQuantity() + "," +
                                 order.getStatus() + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to save orders.");
        }
    }
}
