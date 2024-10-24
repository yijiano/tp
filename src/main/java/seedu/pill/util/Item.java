package seedu.pill.util;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Represents an item in the inventory.
 */
public class Item implements Comparable<Item> {
    private String name;
    private int quantity;
    private Optional<LocalDate> expiryDate;

    public Item(String name, int quantity) {
        this(name, quantity, null);
    }

    public Item(String name, int quantity, LocalDate expiryDate) {
        this.name = name;
        this.quantity = quantity;
        this.expiryDate = Optional.<LocalDate>ofNullable(expiryDate);
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public Optional<LocalDate> getExpiryDate() {
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
        return this.getExpiryDate()
                .map(thisDate -> other.getExpiryDate()
                        .map(otherDate -> thisDate.compareTo(otherDate))
                        .orElse(-1))
                .orElseGet(() -> other.getExpiryDate().map(x -> 1).orElse(0));
    }

    @Override
    public String toString() {
        if (quantity <= 0) {
            return name;
        }
        return this.expiryDate
                .map(exDate -> name + ": " + quantity + " in stock, expiring: " + exDate)
                .orElse(name + ": " + quantity + " in stock");
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
