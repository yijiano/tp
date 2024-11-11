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
    private double cost;
    private double price;

    public Item(String name, int quantity) {
        this(name, quantity, null, 0, 0);
    }

    public Item(String name, int quantity, LocalDate expiryDate) {
        this(name, quantity, expiryDate, 0, 0);
    }

    public Item(String name, int quantity, LocalDate expiryDate, double cost, double price) {
        this.name = name;
        this.quantity = quantity;
        this.expiryDate = Optional.ofNullable(expiryDate);
        this.cost = cost;
        this.price = price;
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

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = Optional.ofNullable(expiryDate);
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(": ").append(quantity).append(" in stock");
        expiryDate.ifPresent(date -> sb.append(", expiring: ").append(date));
        if (cost > 0) {
            sb.append(", cost: $").append(String.format("%.2f", cost));
        }
        if (price > 0) {
            sb.append(", price: $").append(String.format("%.2f", price));
        }
        return sb.toString();
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Item item) {
            return name.equals(item.getName()) && quantity == item.getQuantity()
                    && expiryDate.equals(item.getExpiryDate())
                    && Double.compare(cost, item.cost) == 0
                    && Double.compare(price, item.price) == 0;
        }
        return false;
    }
}
