package seedu.pill.util;

import seedu.pill.exceptions.ExceptionMessages;
import seedu.pill.exceptions.PillException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Manages all transactions and orders in the inventory management system.
 * This class serves as the central point for handling inventory movements,
 * both incoming (purchases) and outgoing (dispensing) transactions, as well as
 * managing orders and their fulfillment.
 *
 * The TransactionManager maintains a complete audit trail of all inventory changes
 * and ensures data consistency between transactions and the actual inventory state.
 */
public class TransactionManager {
    private final List<Transaction> transactions;
    private final List<Order> orders;
    private final ItemMap itemMap;

    /**
     * Constructs a new TransactionManager with a reference to the system's inventory.
     *
     * @param itemMap - The inventory system's ItemMap instance to track and modify stock levels
     */
    public TransactionManager(ItemMap itemMap) {
        this.transactions = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.itemMap = itemMap;
    }

    /**
     * Creates and processes a new transaction in the system.
     * This method handles both incoming and outgoing transactions, updating the inventory accordingly.
     * For incoming transactions, it adds new stock to the inventory.
     * For outgoing transactions, it verifies sufficient stock and removes items from inventory,
     * prioritizing items with earlier expiry dates.
     *
     * @param itemName        - The name of the item involved in the transaction
     * @param quantity        - The quantity of items being transacted
     * @param type            - The type of transaction (INCOMING or OUTGOING)
     * @param notes           - Additional notes or comments about the transaction
     * @param associatedOrder - The order associated with this transaction, if any
     * @return                - The created Transaction object
     * @throws PillException  - If there's insufficient stock for an outgoing transaction
     *                          or if any other validation fails
     */
    public Transaction createTransaction(String itemName, int quantity,
                                         Transaction.TransactionType type,
                                         String notes, Order associatedOrder) throws PillException {

        Transaction transaction = new Transaction(itemName, quantity, type, notes, associatedOrder);

        if (type == Transaction.TransactionType.INCOMING) {
            Item item = new Item(itemName, quantity);
            itemMap.addItem(item);
        } else {
            TreeSet<Item> items = itemMap.get(itemName);
            int totalQuantity = items.stream()
                    .mapToInt(Item::getQuantity)
                    .sum();

            if (totalQuantity < quantity) {
                throw new PillException(ExceptionMessages.INVALID_QUANTITY);
            }

            int remainingQty = quantity;
            while (remainingQty > 0) {
                Item item = items.first();
                if (item.getQuantity() <= remainingQty) {
                    remainingQty -= item.getQuantity();
                    itemMap.deleteItem(itemName, item.getExpiryDate());
                } else {
                    item.setQuantity(item.getQuantity() - remainingQty);
                    remainingQty = 0;
                }
            }
        }

        transactions.add(transaction);
        return transaction;
    }

    /**
     * Creates a new order in the system.
     * Orders can be either purchase orders (for receiving stock) or dispense orders (for dispensing items).
     *
     * @param type  - The type of order (PURCHASE or DISPENSE)
     * @param notes - Any additional notes or comments about the order
     * @return      - The created Order object
     */
    public Order createOrder(Order.OrderType type, String notes) {
        Order order = new Order(type, notes);
        orders.add(order);
        return order;
    }

    /**
     * Fulfills a pending order by creating appropriate transactions for each item in the order.
     * This method processes all items in the order and updates the inventory accordingly.
     * For purchase orders, it creates incoming transactions.
     * For dispense orders, it creates outgoing transactions.
     *
     * @param order          - The order to be fulfilled
     * @throws PillException - If the order is not in PENDING status or if there's insufficient
     *                         stock for any item in a dispense order
     */
    public void fulfillOrder(Order order) throws PillException {
        if (order.getStatus() != Order.OrderStatus.PENDING) {
            throw new PillException(ExceptionMessages.INVALID_COMMAND);
        }

        Transaction.TransactionType transactionType = order.getType() == Order.OrderType.PURCHASE
                ? Transaction.TransactionType.INCOMING
                : Transaction.TransactionType.OUTGOING;

        for (OrderItem item : order.getItems()) {
            createTransaction(
                    item.getItemName(),
                    item.getQuantity(),
                    transactionType,
                    "Order fulfillment",
                    order
            );
        }

        order.fulfill();
    }

    /**
     * Returns a copy of the complete transaction history.
     *
     * @return - A new ArrayList containing all transactions
     */
    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }

    /**
     * Returns a copy of all orders in the system.
     *
     * @return - A new ArrayList containing all orders
     */
    public List<Order> getOrders() {
        return new ArrayList<>(orders);
    }

    /**
     * Retrieves all transactions related to a specific item.
     *
     * @param itemName - The name of the item to find transactions for
     * @return         - A list of all transactions involving the specified item
     */
    public List<Transaction> getItemTransactions(String itemName) {
        return transactions.stream()
                .filter(t -> t.getItemName().equals(itemName))
                .toList();
    }

    /**
     * Retrieves all transactions that occurred within a specified time period.
     *
     * @param start - The start date/time of the period (inclusive)
     * @param end   - The end date/time of the period (inclusive)
     * @return      - A list of transactions that occurred within the specified period
     */
    public List<Transaction> getTransactionHistory(LocalDateTime start, LocalDateTime end) {
        return transactions.stream()
                .filter(t -> !t.getTimestamp().isBefore(start) && !t.getTimestamp().isAfter(end))
                .toList();
    }
}
