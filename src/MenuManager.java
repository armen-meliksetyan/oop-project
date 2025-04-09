import java.io.*;
import java.util.*;

public class MenuManager {
    private List<MenuItem> menuItems = new ArrayList<>();

    public void addItem(MenuItem item) {
        menuItems.add(item);
    }

    public void removeItem(int id) {
        for (int i = 0; i < menuItems.size(); i++) {
            if (menuItems.get(i).getId() == id) {
                menuItems.remove(i);
                break;
            }
        }
    }

    public List<MenuItem> getMenu() {
        return menuItems;
    }

    public void save() {
        try (PrintWriter writer = new PrintWriter("menu.csv")) {
            for (MenuItem item : menuItems) {
                writer.println(item.getId() + "," + item.getName() + "," + item.getPrice() + "," + item.getCategory());
            }
        } catch (IOException e) {
            System.out.println("Error saving menu.");
        }
    }

    public void load() {
        menuItems.clear();
        try (Scanner scanner = new Scanner(new File("menu.csv"))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length == 4) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    String category = parts[3];
                    menuItems.add(new MenuItem(id, name, price, category));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading menu.");
        }
    }
}
