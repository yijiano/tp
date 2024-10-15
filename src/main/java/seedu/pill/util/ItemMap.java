package seedu.pill.util;

import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Represents a list of items and provides methods to add, delete, list, and edit items.
 */
public class ItemMap implements Iterable<Map.Entry<String, Item>> {
    private static final Logger LOGGER = Logger.getLogger(ItemMap.class.getName());
    private Map<String, Item> items;

    /**
     * Constructor for ItemList.
     * Initializes the internal ArrayList to store items.
     */
    public ItemMap() {
        this.items = new LinkedHashMap<>();
        LOGGER.setLevel(Level.OFF);
        LOGGER.info("New ItemMap instance created");
    }

    @Override
    public Iterator<Map.Entry<String, Item>> iterator() {
        return items.entrySet().iterator();
    }

    /**
     * Adds a new item to the list.
     *
     * @param name     The name of the item.
     * @param quantity The quantity of the item.
     */
    public void addItem(String name, int quantity) {
        assert name != null && !name.trim().isEmpty() : "Item name cannot be null or empty";
        assert quantity > 0 : "Quantity must be positive";

        if (name == null || name.trim().isEmpty() || quantity <= 0) {
            LOGGER.warning("Attempt to add invalid item: name=" + name + ", quantity=" + quantity);
            System.out.println("Invalid item name or quantity.");
            return;
        }

        if (items.containsKey(name)) {
            int newQuantity = items.get(name).getQuantity() + quantity;
            items.replace(name, new Item(name, newQuantity));
            LOGGER.info("Updated existing item: " + name + ", new quantity: " + newQuantity);
            System.out.println("Item already exists. Adding to current stock: \n"
                    + name + ": " + newQuantity + " in stock");
        } else {
            items.put(name, new Item(name, quantity));
            LOGGER.info("Added new item: " + name + ", quantity: " + quantity);
            System.out.println("Added the following item to the inventory: \n"
                    + name + ": " + quantity + " in stock");
        }
    }

    /**
     * Adds a new item to the list. Does not print any output.
     *
     * @param name     The name of the item.
     * @param quantity The quantity of the item.
     */
    public void addItemSilent(String name, int quantity) {
        assert name != null && !name.trim().isEmpty() : "Item name cannot be null or empty";
        assert quantity > 0 : "Quantity must be positive";

        if (name == null || name.trim().isEmpty() || quantity <= 0) {
            LOGGER.warning("Attempt to silently add invalid item: name=" + name + ", quantity=" + quantity);
            return;
        }

        if (items.containsKey(name)) {
            int newQuantity = items.get(name).getQuantity() + quantity;
            items.replace(name, new Item(name, newQuantity));
            LOGGER.fine("Silently updated existing item: " + name + ", new quantity: " + newQuantity);
        } else {
            items.put(name, new Item(name, quantity));
            LOGGER.fine("Silently added new item: " + name + ", quantity: " + quantity);
        }
    }

    /**
     * Deletes an item from the list by its name.
     *
     * @param name The name of the item to be deleted.
     */
    public void deleteItem(String name) {
        assert name != null : "Item name cannot be null";

        if (name == null || name.trim().isEmpty()) {
            LOGGER.warning("Attempt to delete item with invalid name: " + name);
            System.out.println("Invalid item name.");
            return;
        }

        Item removedItem = items.remove(name);
        if (removedItem != null) {
            LOGGER.info("Deleted item: " + name + ", quantity: " + removedItem.getQuantity());
            System.out.println("Deleted the following item from the inventory: \n"
                    + name + ": " + removedItem.getQuantity());
        } else {
            LOGGER.warning("Attempt to delete non-existent item: " + name);
            System.out.println("Item not found: " + name);
        }
    }

    /**
     * Edits an item by its name.
     *
     * @param name     The name of the item to be edited.
     * @param quantity The new quantity for the item.
     */
    public void editItem(String name, int quantity) {
        assert name != null && !name.trim().isEmpty() : "Item name cannot be null or empty";
        assert quantity > 0 : "Quantity must be positive";

        if (name == null || name.trim().isEmpty() || quantity <= 0) {
            LOGGER.warning("Attempt to edit item with invalid parameters: name=" + name + ", quantity=" + quantity);
            System.out.println("Invalid item name or quantity.");
            return;
        }

        Item item = items.get(name);
        if (item != null) {
            item.setQuantity(quantity);
            LOGGER.info("Edited item: " + name + ", new quantity: " + quantity);
            System.out.println("Edited item: " + name + " (" + quantity + " in stock)");
        } else {
            LOGGER.warning("Attempt to edit non-existent item: " + name);
            System.out.println("Item not found: " + name);
        }
    }

    /**
     * Lists all the items in the inventory.
     */
    public void listItems() {
        if (items.isEmpty()) {
            LOGGER.info("Attempted to list items, but inventory is empty");
            System.out.println("The inventory is empty.");
            return;
        }
        LOGGER.info("Listing all items in inventory");
        System.out.println("Listing all items:");
        int index = 1;
        for (Map.Entry<String, Item> entry : items.entrySet()) {
            Item item = entry.getValue();
            System.out.println(index + ". " + item.getName() + ": " + item.getQuantity() + " in stock");
            index++;
        }
    }

    /**
     * Finds an item in the list.
     *
     * @param itemName The name of the item.
     */
    public ItemMap findItem(String itemName) {
        assert itemName != null : "Item name cannot be null";

        ItemMap foundItems = new ItemMap();
        if (itemName == null || itemName.trim().isEmpty()) {
            LOGGER.warning("Attempt to find item with null or empty name");
            return foundItems;
        }
        LOGGER.info("Searching for items containing: " + itemName);
        for (Map.Entry<String, Item> entry : items.entrySet()) {
            Item item = entry.getValue();
            if (item.getName().toLowerCase().contains(itemName.toLowerCase())) {
                foundItems.addItemSilent(item.getName(), item.getQuantity());
            }
        }
        LOGGER.info("Found " + foundItems.items.size() + " items matching: " + itemName);
        return foundItems;
    }
}
