package core;

public class MenuItem {

    private static int nextId = 0;

    private final int id;
    private String name;
    private double price;
    private String description;
    private String category;

    public MenuItem(String name, double price, String category, String description) {
        this.id = nextId++;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public MenuItem(MenuItem item) {
        this.id = item.id;
        this.name = item.name;
        this.description = item.description;
        this.price = item.price;
        this.category = item.category;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public String getDescription() {
        return this.description;
    }

    public String getCategory() {
        return this.category;
    }

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
