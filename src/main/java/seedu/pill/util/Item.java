package seedu.pill.util;

/**
 * Represents an item in the inventory.
 */
public class Item {
    private String name;
    private int quantity;

    public Item(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return name + ": " + quantity + " in stock";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Item) {
            Item item = (Item) obj;
            return name.equals(item.getName()) && quantity == item.getQuantity();
        }
        return false;
    }
}
