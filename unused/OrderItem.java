//@@author philip1304-unused
/*
 * We ended up using regular Items in another ItemMap, rather than using a
 * different type of item.
 */
package seedu.pill.util;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Represents an item in an order with its quantity and optional expiry date.
 * OrderItems are immutable to ensure data consistency throughout the order lifecycle.
 */
public class OrderItem {
    private final String itemName;
    private final int quantity;
    private final Optional<LocalDate> expiryDate;
    private final double unitPrice;

    /**
     * Constructs a new OrderItem with the specified name and quantity.
     *
     * @param itemName                  - The name of the item
     * @param quantity                  - The quantity ordered
     * @throws IllegalArgumentException - If quantity is negative or item name is null/empty
     */
    public OrderItem(String itemName, int quantity) {
        this(itemName, quantity, null, 0.0);
    }

    /**
     * Constructs a new OrderItem with all properties specified.
     *
     * @param itemName                  - The name of the item
     * @param quantity                  - The quantity ordered
     * @param expiryDate                - The expiry date of the item (can be null)
     * @param unitPrice                 - The price per unit of the item
     * @throws IllegalArgumentException - If quantity is negative, price is negative, or item name is null/empty
     */
    public OrderItem(String itemName, int quantity, LocalDate expiryDate, double unitPrice) {
        if (itemName == null || itemName.trim().isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be null or empty");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        if (unitPrice < 0) {
            throw new IllegalArgumentException("Unit price cannot be negative");
        }

        this.itemName = itemName;
        this.quantity = quantity;
        this.expiryDate = Optional.ofNullable(expiryDate);
        this.unitPrice = unitPrice;
    }

    /**
     * Gets the name of the item.
     *
     * @return - The item name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Gets the quantity ordered.
     *
     * @return - The quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Gets the expiry date of the item, if any.
     *
     * @return - An Optional containing the expiry date, or empty if no expiry date
     */
    public Optional<LocalDate> getExpiryDate() {
        return expiryDate;
    }

    /**
     * Gets the unit price of the item.
     *
     * @return - The price per unit
     */
    public double getUnitPrice() {
        return unitPrice;
    }

    /**
     * Calculates the total price for this order item.
     *
     * @return - The total price (quantity * unit price)
     */
    public double getTotalPrice() {
        return quantity * unitPrice;
    }

    /**
     * Returns a string representation of this OrderItem.
     * The string includes the item name, quantity, expiry date (if present),
     * and pricing information (if unit price is greater than 0).
     *
     * @return - A formatted string containing the OrderItem's details
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append(itemName)
                .append(": ")
                .append(quantity)
                .append(" units");

        expiryDate.ifPresent(date -> sb.append(", expires: ").append(date));

        if (unitPrice > 0) {
            sb.append(String.format(", unit price: $%.2f", unitPrice))
                    .append(String.format(", total: $%.2f", getTotalPrice()));
        }

        return sb.toString();
    }

    /**
     * Compares this OrderItem with another object for equality.
     * Two OrderItems are considered equal if they have the same item name,
     * quantity, expiry date, and unit price.
     *
     * @param obj - The object to compare with this OrderItem
     * @return    - True if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof OrderItem other)) {
            return false;
        }
        return itemName.equals(other.itemName) &&
                quantity == other.quantity &&
                expiryDate.equals(other.expiryDate) &&
                Double.compare(unitPrice, other.unitPrice) == 0;
    }

    /**
     * Generates a hash code for this OrderItem.
     * The hash code is calculated using all fields of the OrderItem
     * to ensure it follows the contract with equals().
     *
     * @return - A hash code value for this OrderItem
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + itemName.hashCode();
        result = 31 * result + quantity;
        result = 31 * result + expiryDate.hashCode();
        result = 31 * result + Double.hashCode(unitPrice);
        return result;
    }
}
