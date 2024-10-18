package seedu.pill.util;

import java.time.LocalDate;

/**
 * Represents an item in the inventory.
 */
public class Item {
    private String name;
    private int quantity;
    private LocalDate expiryDate;

    public Item(String name, int quantity) {
        new Item(name, quantity, null);
    }

    public Item(String name, int quantity, LocalDate expiryDate) {
        this.name = name;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
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
