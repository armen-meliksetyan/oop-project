package core;

import exceptions.ItemExistsException;
import java.util.ArrayList;
import utils.FileUtils;

public class MenuManager {

    private ArrayList<MenuItem> menuItems = new ArrayList<>();

    public void addItem(MenuItem item) throws ItemExistsException {
        for (MenuItem menuItem : menuItems) {
            if (menuItem.equals(item)) {
                throw new ItemExistsException("The item with the name " + item.getName() + " price " + item.getPrice() + " category " + item.getCategory() + " and with description " + item.getDescription() + " already exists.");
            }
        }
        menuItems.add(item);
    }

    public void removeItem(int id) {
        menuItems.removeIf(item -> item.getId() == id);
    }

    public ArrayList<MenuItem> getMenu() {
        ArrayList<MenuItem> copy = new ArrayList<>();
        for (MenuItem item : menuItems) {
            copy.add(new MenuItem(item));
        }
        return copy;
    }

    public void save() {
        ArrayList<String> lines = new ArrayList<>();
        for (MenuItem item : menuItems) {
            lines.add(item.getName() + ","
                    + item.getPrice() + ","
                    + item.getCategory() + ","
                    + item.getDescription());
        }
        FileUtils.writeLines(lines, "menu.csv");
    }

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
