package seedu.pill.util;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Map;
import java.util.TreeSet;

/**
 * Represents an order in the inventory management system.
 * An order is a collection of items that are either being received into inventory (purchase orders)
 * or being dispensed out of inventory (dispense orders). Each order has a unique identifier,
 * tracks its creation and fulfillment times, maintains a status, and can include notes.
 */
public class Order {
    private final UUID id;
    private final OrderType type;
    private final LocalDateTime creationTime;
    private LocalDateTime fulfillmentTime;
    private OrderStatus status;
    private final ItemMap items;
    private final String notes;

    /**
     * Defines the types of orders that can be created in the system.
     * PURCHASE - orders represent incoming inventory from suppliers.
     * DISPENSE - orders represent outgoing inventory to end users or departments.
     */
    public enum OrderType {
        PURCHASE,
        DISPENSE
    }

    /**
     * Defines the possible states of an order in the system.
     * PENDING   - indicates the order is awaiting processing.
     * FULFILLED - indicates the order has been successfully processed and completed.
     * CANCELLED - indicates the order was terminated before completion.
     */
    public enum OrderStatus {
        PENDING,
        FULFILLED,
        CANCELLED
    }

    /**
     * Creates a new Order with the specified type and notes.
     * The order is automatically assigned a unique identifier and initialized
     * with a PENDING status and the current timestamp.
     *
     * @param type  - The type of order (PURCHASE or DISPENSE)
     * @param notes - Additional information or comments about the order
     */
    public Order(OrderType type, String notes) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.creationTime = LocalDateTime.now();
        this.status = OrderStatus.PENDING;
        this.items = new ItemMap();
        this.notes = notes;
    }

    /**
     * Creates a new Order with the specified type and notes.
     * The order is automatically assigned a unique identifier and initialized
     * with a PENDING status and the current timestamp.
     *
     * @param type  - The type of order (PURCHASE or DISPENSE)
     * @param notes - Additional information or comments about the order
     */
    public Order(OrderType type, ItemMap itemsToOrder, String notes) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.creationTime = LocalDateTime.now();
        this.status = OrderStatus.PENDING;
        this.items = itemsToOrder;
        this.notes = notes;
    }

    /**
     * Adds an item to this order with the specified name and quantity.
     * Multiple items can be added to a single order.
     *
     * @param item - The item to add
     */
    public void addItem(Item item) {
        items.addItemSilent(item);
    }

    /**
     * Marks this order as fulfilled, updating its status and recording
     * the fulfillment timestamp.
     */
    public void fulfill() {
        this.status = OrderStatus.FULFILLED;
        this.fulfillmentTime = LocalDateTime.now();
    }

    /**
     * Marks this order as cancelled, preventing it from being fulfilled.
     */
    public void cancel() {
        this.status = OrderStatus.CANCELLED;
    }

    /**
     * Returns the unique identifier for this order.
     *
     * @return - The UUID assigned to this order
     */
    public UUID getId() {
        return id;
    }

    /**
     * Returns the type of this order (PURCHASE or DISPENSE).
     *
     * @return - The OrderType of this order
     */
    public OrderType getType() {
        return type;
    }

    /**
     * Returns the timestamp when this order was created.
     *
     * @return - The creation timestamp
     */
    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    /**
     * Returns the timestamp when this order was fulfilled, if applicable.
     *
     * @return - The fulfillment timestamp, or null if the order is not fulfilled
     */
    public LocalDateTime getFulfillmentTime() {
        return fulfillmentTime;
    }

    /**
     * Returns the current status of this order.
     *
     * @return - The current OrderStatus
     */
    public OrderStatus getStatus() {
        return status;
    }

    /**
     * Returns a copy of the list of items in this order.
     * The returned list is a new ArrayList to prevent modification of the original items.
     *
     * @return - A new ArrayList containing the OrderItems in this order
     */
    public ItemMap getItems() {
        return items;
    }

    /**
     * Returns the notes associated with this order.
     *
     * @return - The notes string provided during order creation
     */
    public String getNotes() {
        return notes;
    }
    /**
     * Lists the details of the order, including UUID, type, creation time, fulfillment time,
     * status, notes, and the items in the order with a serial number for each item.
     *
     * <p>This method prints the order's metadata such as UUID, type, creation time, fulfillment time,
     * status, and notes. It then iterates through the items in the order, printing each item's details
     * with a serial number (starting from 1) to differentiate the items within the order.</p>
     */
    public void listItems() {
        int index = 1;
        System.out.println("UUID: " + id);
        System.out.println("Type: " + type);
        System.out.println("Creation Time: " + creationTime);
        System.out.println("Fulfillment Time: " + fulfillmentTime);
        System.out.println("Status: " + status);
        System.out.println("Notes: " + notes);
        System.out.println("Items: ");
        for (Map.Entry<String, TreeSet<Item>> entry : items.items.entrySet()) {
            TreeSet<Item> itemSet = entry.getValue();
            for (Item item : itemSet) {
                System.out.println(index + ". " + item.toString());
                index++;
            }
        }
    }
}
