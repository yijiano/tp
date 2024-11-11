package seedu.pill.util;

import seedu.pill.exceptions.ExceptionMessages;
import seedu.pill.exceptions.PillException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.IntStream;

/**
 * Manages all transactions and orders in the inventory management system. This class serves as the central point for
 * handling inventory movements, both incoming (purchases) and outgoing (dispensing) transactions, as well as managing
 * orders and their fulfillment.
 * <p>
 * The TransactionManager maintains a complete audit trail of all inventory changes and ensures data consistency between
 * transactions and the actual inventory state.
 */
public class TransactionManager {
    private final List<Transaction> transactions;
    private final List<Order> orders;
    private final ItemMap itemMap;
    private final Storage storage;

    /**
     * Constructs a new TransactionManager with a reference to the system's inventory.
     *
     * @param itemMap - The inventory system's ItemMap instance to track and modify stock levels
     */
    public TransactionManager(ItemMap itemMap, Storage storage) {
        this.transactions = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.itemMap = itemMap;
        this.storage = storage;
    }

    /**
     * Creates and processes a new transaction in the system. This method handles both incoming and outgoing
     * transactions, updating the inventory accordingly. For incoming transactions, it adds new stock to the inventory.
     * For outgoing transactions, it verifies sufficient stock and removes items from inventory, prioritizing items with
     * earlier expiry dates.
     *
     * @param itemName        - The name of the item involved in the transaction
     * @param quantity        - The quantity of items being transacted
     * @param type            - The type of transaction (INCOMING or OUTGOING)
     * @param notes           - Additional notes or comments about the transaction
     * @param associatedOrder - The order associated with this transaction, if any
     * @return - The created Transaction object
     * @throws PillException - If there's insufficient stock for an outgoing transaction or if any other validation
     *                       fails
     */
    public Transaction createTransaction(String itemName, int quantity, LocalDate expiryDate,
                                         Transaction.TransactionType type,
                                         String notes, Order associatedOrder) throws PillException {

        Transaction transaction = new Transaction(itemName, quantity, type, notes, associatedOrder);

        if (type == Transaction.TransactionType.INCOMING) {
            Item item = new Item(itemName, quantity, expiryDate);
            itemMap.addItem(item);
        } else {
            itemMap.useItem(itemName, quantity);
        }
        storage.saveItemMap(itemMap);

        transactions.add(transaction);
        return transaction;
    }

    /**
     * Creates a new order in the system. Orders can be either purchase orders (to receive stock) or dispense orders
     * (to provide items to customers).
     *
     * @param type         - The type of order (PURCHASE or DISPENSE).
     * @param itemsToOrder - The items to be included in this order.
     * @param notes        - Any additional notes or comments about the order.
     * @return             - The created Order object.
     */
    public Order createOrder(Order.OrderType type, ItemMap itemsToOrder, String notes) {
        Order order = new Order(type, itemsToOrder, notes);
        orders.add(order);
        System.out.println("Order placed! Listing order details");
        order.listItems();
        return order;
    }

    /**
     * Fulfills a pending order by creating appropriate transactions for each item in the order. This method processes
     * all items in the order and updates the inventory accordingly. For purchase orders, it creates incoming
     * transactions. For dispense orders, it creates outgoing transactions.
     *
     * @param order - The order to be fulfilled
     * @throws PillException - If the order is not in PENDING status or if there's insufficient stock for any item in a
     *                       dispense order
     */
    public void fulfillOrder(Order order) throws PillException {
        if (order.getStatus() != Order.OrderStatus.PENDING) {
            throw new PillException(ExceptionMessages.ORDER_NOT_PENDING);
        }

        Transaction.TransactionType transactionType = order.getType() == Order.OrderType.PURCHASE
                ? Transaction.TransactionType.INCOMING
                : Transaction.TransactionType.OUTGOING;

        for (Map.Entry<String, TreeSet<Item>> entry : order.getItems().items.entrySet()) {
            TreeSet<Item> itemSet = entry.getValue();
            try {
                itemSet.forEach(item -> {
                    try {
                        createTransaction(
                                item.getName(),
                                item.getQuantity(),
                                item.getExpiryDate().orElse(null),
                                transactionType,
                                "Order fulfillment",
                                order
                        );
                    } catch (PillException e) {
                        throw new RuntimeException("Error creating transaction", e);
                    }
                });
            } catch (RuntimeException e) {
                throw new PillException(ExceptionMessages.TRANSACTION_ERROR);
            }
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
     * @return - A list of all transactions involving the specified item
     */
    public List<Transaction> getItemTransactions(String itemName) {
        return transactions.stream()
                .filter(t -> t.getItemName().equals(itemName))
                .toList();
    }

    /**
     * Lists all transactions by printing each transaction with a numbered format.
     *
     * <p>This method retrieves a list of {@link Transaction} objects using {@link #getTransactions()}.
     * It then iterates through the list, printing each transaction with an index in the format "1. transaction
     * details", "2. transaction details", etc.</p>
     */
    public void listTransactions() {
        List<Transaction> transactions = getTransactions();
        if (transactions.isEmpty()) {
            System.out.println("No transactions found");
        } else {
            IntStream.rangeClosed(1, transactions.size())
                    .forEach(i -> System.out.println(i + ". " + transactions.get(i - 1).toString()));
        }
    }

    /**
     * Lists all current orders by printing the items in each order.
     *
     * <p>This method retrieves a list of {@link Order} objects using {@link #getOrders()}.
     * It then iterates through each order, invoking {@code listItems()} on each order to print the details of its items
     * to the console.</p>
     */
    public void listOrders() {
        List<Order> orders = getOrders();
        if (orders.isEmpty()) {
            System.out.println("No orders recorded...");
        } else {
            IntStream.rangeClosed(1, orders.size())
                    .forEach(i -> {
                        System.out.print(i + ". ");
                        orders.get(i - 1).listItems();
                        System.out.println();
                    });
        }
    }

    /**
     * Retrieves all transactions that occurred within a specified time period.
     *
     * @param start - The start date of the period (inclusive)
     * @param end   - The end date of the period (inclusive)
     * @return - A list of transactions that occurred within the specified period
     */
    public List<Transaction> getTransactionHistory(LocalDate start, LocalDate end) {
        return transactions.stream()
                .filter(t -> !t.getTimestamp().toLocalDate().isBefore(start) && !t.getTimestamp().toLocalDate()
                        .isAfter(end))
                .toList();
    }

    /**
     * Lists the transaction history within the specified date range by printing each transaction to the console
     * with a numbered format.
     *
     * @param start The start of the date range for retrieving transactions.
     * @param end   The end of the date range for retrieving transactions.
     */
    public void listTransactionHistory(LocalDate start, LocalDate end) {
        List<Transaction> transactions = getTransactionHistory(start, end);
        IntStream.rangeClosed(1, transactions.size())
                .forEach(i -> System.out.println(i + ". " + transactions.get(i - 1).toString()));
    }
}
