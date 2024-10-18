package seedu.pill.util;

import java.time.LocalDate;

/**
 * Represents an item in the inventory.
 */
public class Item implements Comparable<Item> {
    private String name;
    private int quantity;
    private LocalDate expiryDate;

    public Item(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
        this.expiryDate = null;
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

    /**
     * Compares this {@code Item} with another {@code Item} based on their expiry dates.
     * The comparison is in ascending order, meaning items with sooner expiry dates will come first.
     *
     * @param other The {@code Item} to be compared to this {@code Item}.
     * @return A negative integer if this item expires sooner than the other;
     *         a positive integer if this item expires later than the other;
     *         zero if both items have the same expiry date.
     */
    @Override
    public int compareTo(Item other) {
        if (this.expiryDate == null && other.expiryDate == null) {
            return 0;
        }
        if (this.expiryDate == null) {
            return 1; // Null (no expiry) is considered later than any date
        }
        if (other.expiryDate == null) {
            return -1; // Any date is considered earlier than null (no expiry)
        }
        return expiryDate.compareTo(other.expiryDate);
    }

    @Override
    public String toString() {
        if (quantity <= 0) {
            return name;
        }
        if (expiryDate == null) {
            return name + ": " + quantity + " in stock";
        }
        return name + ": " + quantity + " in stock, expiring: " + expiryDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Item item) {
            return name.equals(item.getName()) && quantity == item.getQuantity()
                    && expiryDate.equals(item.getExpiryDate());
        }
        return false;
    }
}
