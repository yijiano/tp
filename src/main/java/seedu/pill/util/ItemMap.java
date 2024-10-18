package seedu.pill.util;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Represents a list of items and provides methods to add, delete, list, and edit items.
 */
public class ItemMap implements Iterable<Map.Entry<String, TreeSet<Item>>> {
    private static final Logger LOGGER = Logger.getLogger(ItemMap.class.getName());
    private Map<String, TreeSet<Item>> items;

    /**
     * Constructor for ItemMap.
     * Initializes the internal Map to store items.
     */
    public ItemMap() {
        this.items = new LinkedHashMap<>();
        LOGGER.setLevel(Level.OFF);
        LOGGER.info("New ItemMap instance created");
    }

    @Override
    public Iterator<Map.Entry<String, TreeSet<Item>>> iterator() {
        return items.entrySet().iterator();
    }

    /**
     * Adds a new item to the list.
     *
     * @param name       The name of the item.
     * @param quantity   The quantity of the item.
     * @param expiryDate The expiry date of the item. Null value represents no expiry date.
     */
    public void addItem(String name, int quantity, LocalDate expiryDate) {
        assert name != null && !name.trim().isEmpty() : "Item name cannot be null or empty";
        assert quantity > 0 : "Quantity must be positive";

        if (name == null || name.trim().isEmpty() || quantity <= 0) {
            LOGGER.warning("Attempt to add invalid item: name=" + name + ", quantity=" + quantity);
            System.out.println("Invalid item name or quantity.");
            return;
        }

        // If the item name exists, check for items with the same expiry date
        if (items.containsKey(name)) {
            TreeSet<Item> itemSet = items.get(name);
            boolean itemUpdated = false;

            // Check if an item with the same expiry date already exists
            for (Item item : itemSet) {
                if (item.getExpiryDate().equals(expiryDate)) {
                    int newQuantity = item.getQuantity() + quantity;
                    item.setQuantity(newQuantity);
                    itemUpdated = true;
                    LOGGER.info("Updated existing item with expiry date: " + name + ", new quantity: " + newQuantity + ", expiry: " + expiryDate);
                    System.out.println("Item already exists with the same expiry date. Updated quantity: \n"
                            + name + ": " + newQuantity + " in stock (Expiry: " + expiryDate + ")");
                    break;
                }
            }

            // If no item with the same expiry date, add a new one
            if (!itemUpdated) {
                itemSet.add(new Item(name, quantity, expiryDate));
                LOGGER.info("Added new item with different expiry date: " + name + ", quantity: " + quantity + ", expiry: " + expiryDate);
                System.out.println("Added new item with a different expiry date: \n"
                        + name + ": " + quantity + " in stock (Expiry: " + expiryDate + ")");
            }
        } else {
            // If the item doesn't exist, create a new list for the item and add it
            TreeSet<Item> itemSet = new TreeSet<>();
            Item item = new Item(name, quantity, expiryDate);
            itemSet.add(item);
            items.put(name, itemSet);
            LOGGER.info("Added new item: " + name + ", quantity: " + quantity + ", expiry: " + expiryDate);
            System.out.println("Added the following item to the inventory: \n"
                    + item);
        }
    }

    /**
     * Adds a new item to the list. Does not print any output.
     *
     * @param name       The name of the item.
     * @param quantity   The quantity of the item.
     * @param expiryDate The expiry date of the item. Null value represents no expiry date.
     */
    public void addItemSilent(String name, int quantity, LocalDate expiryDate) {
        assert name != null && !name.trim().isEmpty() : "Item name cannot be null or empty";
        assert quantity > 0 : "Quantity must be positive";

        if (name == null || name.trim().isEmpty() || quantity <= 0) {
            LOGGER.warning("Attempt to silently add invalid item: name=" + name + ", quantity=" + quantity);
            return;
        }

        if (items.containsKey(name)) {
            TreeSet<Item> itemSet = items.get(name);
            boolean itemUpdated = false;
            for (Item item : itemSet) {
                if (item.getExpiryDate().equals(expiryDate)) {
                    int newQuantity = item.getQuantity() + quantity;
                    item.setQuantity(newQuantity);
                    itemUpdated = true;
                    LOGGER.fine("Silently updated existing item: " + name + ", new quantity: " + newQuantity);
                    break;
                }
            }
            if (!itemUpdated) {
                itemSet.add(new Item(name, quantity, expiryDate));
                LOGGER.fine("Silently added new item: " + name + ", quantity: " + quantity);
            }
        } else {
            TreeSet<Item> itemSet = new TreeSet<>();
            Item item = new Item(name, quantity, expiryDate);
            itemSet.add(item);
            items.put(name, itemSet);
            LOGGER.fine("Silently added new item: " + name + ", quantity: " + quantity);
        }
    }

    /**
     * Deletes an item from the list by its name.
     *
     * @param name       The name of the item to be deleted.
     * @param expiryDate The date of the item to be deleted.
     */
    public void deleteItem(String name, LocalDate expiryDate) {
        assert name != null : "Item name cannot be null";

        if (name == null || name.trim().isEmpty()) {
            LOGGER.warning("Attempt to delete item with invalid name: " + name);
            System.out.println("Invalid item name.");
            return;
        }

        TreeSet<Item> itemSet = items.get(name);
        if (itemSet != null) {
            Item dummyItem = new Item(name, 0, expiryDate);
            Item removedItem = itemSet.ceiling(dummyItem);
            if (removedItem != null && removedItem.getExpiryDate().equals(expiryDate)) {
                itemSet.remove(removedItem);
                LOGGER.info("Deleted item: " + removedItem);
                System.out.println("Deleted the following item from the inventory: \n"
                        + removedItem);
                if (itemSet.isEmpty()) {
                    items.remove(name);
                }
            } else {
                LOGGER.warning("Attempt to delete non-existent item: " + removedItem);
                System.out.println("Item not found: " + removedItem);
            }
        } else {
            LOGGER.warning("Attempt to delete non-existent item: " + name);
            System.out.println("Item not found: " + name);
        }
    }

    /**
     * Edits an item by its name.
     *
     * @param name       The name of the item to be edited.
     * @param quantity   The new quantity for the item.
     * @param expiryDate The expiry date of the item.
     */
    public void editItem(String name, int quantity, LocalDate expiryDate) {
        assert name != null && !name.trim().isEmpty() : "Item name cannot be null or empty";
        assert quantity > 0 : "Quantity must be positive";

        if (name == null || name.trim().isEmpty() || quantity <= 0) {
            LOGGER.warning("Attempt to edit item with invalid parameters: name=" + name + ", quantity=" + quantity);
            System.out.println("Invalid item name or quantity.");
            return;
        }

//        Item item = items.get(name);
        TreeSet<Item> itemSet = items.get(name);
        boolean isUpdated = false;
        if (itemSet != null) {
            for (Item item : itemSet) {
                if (item.getExpiryDate().equals(expiryDate)) {
                    item.setQuantity(quantity);
                    isUpdated = true;
                }
            }
            if (isUpdated) {
                LOGGER.info("Edited item: " + name + " Expiry: " + expiryDate + ", new quantity: " + quantity);
                System.out.println("Edited item: " + name + " Expiry: " + expiryDate + " (" + quantity + " in stock)");
            } else {
                LOGGER.warning("Attempt to edit non-existent item: " + name + " Expiry: " + expiryDate);
                System.out.println("Item not found: " + name + " Expiry: " + expiryDate);
            }
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
        for (Map.Entry<String, TreeSet<Item>> entry : items.entrySet()) {
            TreeSet<Item> itemSet = entry.getValue();
            for (Item item : itemSet) {
                System.out.println(index + ". " + item.toString());
                index++;
            }
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
        for (Map.Entry<String, TreeSet<Item>> entry : items.entrySet()) {
            TreeSet<Item> itemSet = entry.getValue();
            if (itemSet.first().getName().toLowerCase().contains(itemName.toLowerCase())) {
                for (Item item : itemSet) {
                    foundItems.addItemSilent(item.getName(), item.getQuantity(), item.getExpiryDate());
                }
            }
        }
        LOGGER.info("Found " + foundItems.items.size() + " items matching: " + itemName);
        return foundItems;
    }
}
