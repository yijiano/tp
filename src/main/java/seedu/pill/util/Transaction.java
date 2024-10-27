package seedu.pill.util;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a transaction in the inventory management system.
 * Each transaction records a change in inventory, either through receiving new items (INCOMING)
 * or dispensing existing items (OUTGOING). Transactions can be associated with an Order or
 * can be direct/manual transactions.
 */
public class Transaction {
    private final UUID id;
    private final String itemName;
    private final int quantity;
    private final TransactionType type;
    private final LocalDateTime timestamp;
    private final String notes;
    private final Order associatedOrder;

    /**
     * Defines the types of transactions possible in the system.
     * INCOMING represents receiving new stock.
     * OUTGOING represents dispensing items.
     */
    public enum TransactionType {
        INCOMING,
        OUTGOING
    }

    /**
     * Creates a new Transaction with the specified details.
     *
     * @param itemName        - The name of the item involved in the transaction
     * @param quantity        - The number of items involved in the transaction
     * @param type            - The type of transaction (INCOMING or OUTGOING)
     * @param notes           - Additional notes or comments about the transaction
     * @param associatedOrder - The order associated with this transaction, if any (can be null)
     */
    public Transaction(String itemName, int quantity, TransactionType type, String notes, Order associatedOrder) {
        this.id = UUID.randomUUID();
        this.itemName = itemName;
        this.quantity = quantity;
        this.type = type;
        this.timestamp = LocalDateTime.now();
        this.notes = notes;
        this.associatedOrder = associatedOrder;
    }

    /**
     * Gets the unique identifier for this transaction.
     *
     * @return - The UUID of this transaction
     */
    public UUID getId() {
        return id;
    }

    /**
     * Gets the name of the item involved in this transaction.
     *
     * @return - The item name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Gets the quantity of items involved in this transaction.
     *
     * @return - The quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Gets the type of this transaction (INCOMING or OUTGOING).
     *
     * @return - The transaction type
     */
    public TransactionType getType() {
        return type;
    }

    /**
     * Gets the timestamp when this transaction was created.
     *
     * @return - The creation timestamp
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Gets any additional notes associated with this transaction.
     *
     * @return - The transaction notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Gets the order associated with this transaction, if any.
     *
     * @return - The associated order, or null if this was a direct transaction
     */
    public Order getAssociatedOrder() {
        return associatedOrder;
    }

    /**
     * Returns a string representation of this transaction, including timestamp,
     * type, quantity, item name, notes, and associated order ID (if any).
     *
     * @return - A formatted string representing this transaction
     */
    @Override
    public String toString() {
        return String.format("[%s] %s: %d %s - %s %s",
                timestamp.toString(),
                type,
                quantity,
                itemName,
                notes,
                associatedOrder != null ? "(Order: " + associatedOrder.getId() + ")" : ""
        );
    }
}
