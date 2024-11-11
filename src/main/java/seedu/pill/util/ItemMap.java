package seedu.pill.util;

import seedu.pill.exceptions.ExceptionMessages;
import seedu.pill.exceptions.PillException;

import java.time.LocalDate;
import java.util.Map;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Logger;
import java.util.stream.IntStream;

/**
 * Represents a list of items and provides methods to add, delete, list, and edit items.
 */
public class ItemMap implements Iterable<Map.Entry<String, TreeSet<Item>>> {
    private static final Logger LOGGER = PillLogger.getLogger();
    Map<String, TreeSet<Item>> items;

    /**
     * Constructor for ItemMap.
     * Initializes the internal Map to store items.
     */
    public ItemMap() {
        this.items = new LinkedHashMap<>();
        LOGGER.info("New ItemMap instance created");
    }

    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    @Override
    public Iterator<Map.Entry<String, TreeSet<Item>>> iterator() {
        return items.entrySet().iterator();
    }

    /**
     * Compares this ItemMap to the specified object for equality.
     *
     * <p>This method returns {@code true} if and only if the specified object
     * is also an ItemMap and both ItemMaps contain the same key-value pairs,
     * where keys are strings and values are sets of Item objects. The equality
     * of Item objects is determined by their own {@link Item#equals(Object)}
     * method.</p>
     *
     * @param obj the object to be compared for equality with this ItemMap
     * @return {@code true} if the specified object is equal to this ItemMap;
     *         {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ItemMap itemMap) {
            return this.items.equals(itemMap.items);
        }
        return false;
    }

    /**
     * Adds a new item to the list.
     *
     * @param newItem The item to be added.
     */
    public void addItem(Item newItem) {
        String name = newItem.getName().toLowerCase();
        int quantity = newItem.getQuantity();
        Optional<LocalDate> expiryDate = newItem.getExpiryDate();

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
                    expiryDate.ifPresentOrElse(
                            expiry -> {
                                LOGGER.info("Updated existing item with expiry date: " + item);
                                System.out.println("Item already exists with the same expiry date. Updated quantity: \n"
                                        + item);
                            },
                            () -> {
                                LOGGER.info("Updated existing item with no expiry date: " + item);
                                System.out.println("Item already exists with no expiry date. Updated quantity: \n"
                                    + item);
                            }
                    );
                    break;
                }
            }

            // If no item with the same expiry date, add a new one
            if (!itemUpdated) {
                itemSet.add(newItem);
                LOGGER.info("Added new item with different expiry date: " + newItem);
                System.out.println("Added new item with a different expiry date: \n"
                        + newItem);
            }
        } else {
            // If the item doesn't exist, create a new list for the item and add it
            TreeSet<Item> itemSet = new TreeSet<>();
            itemSet.add(newItem);
            items.put(name, itemSet);
            LOGGER.info("Added new item: " + newItem);
            System.out.println("Added the following item to the inventory: \n"
                    + newItem);
        }
    }

    /**
     * Adds a new item to the list. Does not print any output.
     *
     * @param newItem The item to be added.
     */
    public void addItemSilent(Item newItem) {
        String name = newItem.getName().toLowerCase();
        int quantity = newItem.getQuantity();
        Optional<LocalDate> expiryDate = newItem.getExpiryDate();

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
                itemSet.add(newItem);
                LOGGER.fine("Silently added new item: " + newItem);
            }
        } else {
            TreeSet<Item> itemSet = new TreeSet<>();
            itemSet.add(newItem);
            items.put(name, itemSet);
            LOGGER.fine("Silently added new item: " + newItem);
        }
    }

    /**
     * Deletes an item from the list by its name.
     *
     * @param itemName The name of the item to be deleted.
     * @param expiryDate The date of the item to be deleted.
     */
    public void deleteItem(String itemName, Optional<LocalDate> expiryDate) {
        String name = itemName.toLowerCase();
        assert name != null : "Item name cannot be null";

        if (name == null || name.trim().isEmpty()) {
            LOGGER.warning("Attempt to delete item with invalid name: " + name);
            System.out.println("Invalid item name.");
            return;
        }

        TreeSet<Item> itemSet = items.get(name);
        if (itemSet != null) {
            Item dummyItem = expiryDate.map(ex -> new Item(name.toLowerCase(), 0, ex))
                            .orElse(new Item(name, 0));
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
     * Edits an item by its name and expiry date.
     *
     * @param updatedItem The updated item that has a new quantity.
     */
    public void editItem(Item updatedItem) {
        String name = updatedItem.getName().toLowerCase();
        int quantity = updatedItem.getQuantity();
        Optional<LocalDate> expiryDate = updatedItem.getExpiryDate();

        assert name != null && !name.trim().isEmpty() : "Item name cannot be null or empty";
        assert quantity > 0 : "Quantity must be positive";

        if (name == null || name.trim().isEmpty() || quantity <= 0) {
            LOGGER.warning("Attempt to edit item with invalid parameters: name=" + name + ", quantity=" + quantity);
            System.out.println("Invalid item name or quantity.");
            return;
        }

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
                LOGGER.info("Edited item: " + updatedItem);
                System.out.println("Edited item: " + updatedItem);
            } else {
                LOGGER.warning("Attempt to edit non-existent item: " + updatedItem);
                System.out.println("Item not found: " + updatedItem);
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
     * Lists all items in this {@code ItemMap} that have expired or are expiring before the specified cutoff date.
     * <p>
     * Retrieves expired or expiring items from {@link #getExpiringItems(LocalDate)}, and logs a message
     * if there are no items meeting the specified date criteria. If there are items that have expired
     * (when {@code cutOffDate} is today) or will expire before {@code cutOffDate} (for a future date),
     * the method logs and prints a message listing these items.
     * </p>
     *
     * @param cutOffDate the date against which item expiry is checked. If the date is today,
     *                   the method lists items that have expired. If the date is in the future,
     *                   it lists items expiring before the cutoff date.
     */
    public void listExpiringItems(LocalDate cutOffDate) {
        ItemMap expiringItems = this.getExpiringItems(cutOffDate);
        if (expiringItems.isEmpty()) {
            if (cutOffDate.isEqual(LocalDate.now())) {
                LOGGER.info("There are no items that have expired.");
                System.out.println("There are no items that have expired.");
            } else {
                LOGGER.info("There are no items expiring before " + cutOffDate + ".");
                System.out.println("There are no items expiring before " + cutOffDate + ".");
            }
            return;
        }
        if (cutOffDate.isEqual(LocalDate.now())) {
            LOGGER.info("Listing all items that have expired");
            System.out.println("Listing all items that have expired");
        } else {
            LOGGER.info("Listing all items expiring before " + cutOffDate);
            System.out.println("Listing all items expiring before " + cutOffDate);
        }
        List<Item> itemList = expiringItems.items.values().stream()
                .flatMap(Collection::stream)
                .toList();

        IntStream.range(0, itemList.size())
                .forEach(i -> System.out.println((i + 1) + ". " + itemList.get(i)));

        System.out.println();
    }

    /**
     * Lists all the items in the inventory for restock command, given a threshold value.
     * Prints all items with quantity strictly less than threshold.
     *
     * @param threshold The minimum number of items before it is deemed to require replenishment.
     */
    public void listItemsToRestock(int threshold){
        try {
            if (items.isEmpty()) {
                LOGGER.info("Attempted to list items, but inventory is empty");
                return;
            }

            if (threshold < 0) {
                throw new PillException(ExceptionMessages.INVALID_QUANTITY);
            }

            List<Item> filteredItems = items.values().stream()
                    .flatMap(TreeSet::stream)
                    .filter(item -> item.getQuantity() <= threshold)
                    .toList();

            if (filteredItems.isEmpty()) {
                LOGGER.info(String.format("There are no items that have quantity less than or equal to %d.",
                        threshold));
                System.out.printf("There are no items that have quantity less than or equal to %d:%n", threshold);

            } else {
                LOGGER.info(String.format("Listing all items that need to be restocked (less than or equal to %d):",
                        threshold));
                System.out.printf("Listing all items that need to be restocked (less than or equal to %d):%n",
                        threshold);
                IntStream.rangeClosed(1, filteredItems.size())
                        .forEach(i -> System.out.println(i + ". " + filteredItems.get(i - 1).toString()));
            }

        } catch (PillException e) {
            LOGGER.severe(e.getMessage());
            PillException.printException(e);
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
                    foundItems.addItemSilent(item);
                }
            }
        }
        LOGGER.info("Found " + foundItems.items.size() + " items matching: " + itemName);
        return foundItems;
    }

    /**
     * Retrieves all items that expire before the cutOffDate from the item map.
     *
     * <p>This method iterates through all items in the item map and checks each item's expiry date.
     * If the expiry date is before the cut off date, the item is added to a new {@code ItemMap}
     * containing only the expiring items.</p>
     *
     * @param cutOffDate date before which all items are considered to be expiring
     * @return an {@code ItemMap} containing all items that are expiring.
     */
    public ItemMap getExpiringItems(LocalDate cutOffDate) {
        ItemMap expiringItems = new ItemMap();
        for (Map.Entry<String, TreeSet<Item>> entry : items.entrySet()) {
            TreeSet<Item> itemSet = entry.getValue();
            itemSet.stream()
                    .flatMap(item -> item.getExpiryDate().stream()
                            .filter(expiry -> expiry.isBefore(cutOffDate))
                            .map(expiry -> item))
                    .forEach(expiringItems::addItemSilent);
        }
        return expiringItems;
    }

    /**
     * Uses a specified quantity of items with the given name, consuming from the earliest expiring items first.
     * If the quantity to use equals or exceeds an item's quantity, that item is deleted.
     * If the quantity to use is less than an item's quantity, the item's quantity is reduced.
     *
     * @param itemName the name of the item to use
     * @param quantityToUse the quantity of the item to consume
     * @throws PillException if:
     *         - the requested quantity exceeds the total available stock for the item
     *         - the specified item name does not exist in the inventory
     */
    public void useItem(String itemName, int quantityToUse) throws PillException {
        itemName = itemName.toLowerCase();
        if (quantityToUse > this.stockCount(itemName)) {
            LOGGER.warning("Attempt to use more items than available: name=" + itemName +
                    ", requested=" + quantityToUse +
                    ", available=" + this.stockCount(itemName));
            throw new PillException(ExceptionMessages.STOCK_UNDERFLOW);
        }

        int originalQuantity = quantityToUse;  // Store for logging purposes
        while (quantityToUse > 0) {
            // throws PillException if no key-value pair for itemName exists
            Item itemToUse = this.getSoonestExpiringItem(itemName);

            if (itemToUse.getQuantity() == quantityToUse) {
                quantityToUse = 0;
                this.deleteItem(itemName, itemToUse.getExpiryDate());

                itemToUse.getExpiryDate().ifPresentOrElse(
                        expiry -> {
                            LOGGER.info("Completely used item with expiry date: " + itemToUse);
                            System.out.println("Completely used item with expiry date " + expiry + ": \n" + itemToUse);
                        },
                        () -> {
                            LOGGER.info("Completely used item: " + itemToUse);
                            System.out.println("Completely used item: \n" + itemToUse);
                        }
                );
            } else if (itemToUse.getQuantity() > quantityToUse) {
                int oldQuantity = itemToUse.getQuantity();
                itemToUse.setQuantity(oldQuantity - quantityToUse);
                this.editItem(itemToUse);
                quantityToUse = 0;

                itemToUse.getExpiryDate().ifPresentOrElse(
                        expiry -> {
                            LOGGER.info("Partially used item with expiry date: " + itemToUse +
                                    " (reduced from " + oldQuantity + " to " + itemToUse.getQuantity() + ")");
                            System.out.println("Partially used item with expiry date " + expiry +
                                    " (reduced from " + oldQuantity + " to " + itemToUse.getQuantity() + "): \n" +
                                    itemToUse);
                        },
                        () -> {
                            LOGGER.info("Partially used item with no expiry date: " + itemToUse +
                                    " (reduced from " + oldQuantity + " to " + itemToUse.getQuantity() + ")");
                            System.out.println("Partially used item with no expiry date" +
                                    " (reduced from " + oldQuantity + " to " + itemToUse.getQuantity() + "): \n" +
                                    itemToUse);
                        }
                );
            } else {
                quantityToUse -= itemToUse.getQuantity();
                this.deleteItem(itemName, itemToUse.getExpiryDate());

                itemToUse.getExpiryDate().ifPresentOrElse(
                        expiry -> {
                            LOGGER.info("Completely used item with expiry date " + expiry
                                    + "(more remaining to use): " + itemToUse);
                        },
                        () -> {
                            // below shouldn't be reachable
                            LOGGER.warning("Completely used all items, but still need to use more.");
                        }
                );
            }
        }
    }

    /**
     * Retrieves the item with the soonest expiry date for the specified item name.
     *
     * @param itemName the name of the item to retrieve
     * @return the {@code Item} with the soonest expiry date
     * @throws PillException If the input string is invalid or there is no such key-value mapping in ItemMap
     */
    public Item getSoonestExpiringItem(String itemName) throws PillException {
        assert itemName != null : "Item name cannot be null";

        if (itemName == null || itemName.trim().isEmpty()) {
            throw new PillException(ExceptionMessages.INVALID_COMMAND);
        }

        TreeSet<Item> item = this.items.get(itemName);

        if (item == null) {
            throw new PillException(ExceptionMessages.NO_ITEM_ERROR);
        }

        return item.first();
    }

    /**
     * Calculates the total quantity in stock for the specified item name.
     * <p>
     * Iterates over all instances of the item to aggregate quantities
     * from each entry, where each {@code Item} represents a distinct batch
     * with an associated quantity.
     * </p>
     *
     * @param itemName the name of the item to query
     * @return the total quantity in stock for the specified item; returns 0 if
     *         the item does not exist or the name is invalid
     */
    public int stockCount(String itemName) {
        assert itemName != null : "Item name cannot be null";

        if (itemName == null || itemName.trim().isEmpty()) {
            return 0;
        }

        TreeSet<Item> item = this.items.get(itemName);

        if (item == null) {
            return 0;
        }

        int totalQuantity = 0;
        for (Item currentItem : item) {
            totalQuantity += currentItem.getQuantity();
        }

        return totalQuantity;
    }

    /**
     * Returns the total number of items in the map.
     * This counts each individual item, including those with different expiry dates.
     *
     * @return The total number of items in the ItemMap.
     */
    public int size() {
        int totalSize = 0;
        for (TreeSet<Item> itemSet : items.values()) {
            totalSize += itemSet.size();
        }
        return totalSize;
    }

    /**
     * Returns a list of all items in the ItemMap.
     *
     * @return A list containing all items in the inventory.
     */
    public List<Item> getAllItems() {
        List<Item> allItems = new ArrayList<>();
        for (TreeSet<Item> itemSet : items.values()) {
            allItems.addAll(itemSet);
        }
        return allItems;
    }


    /**
     * Returns a collection of all items with the specified name.
     * If no items are found, returns an empty collection.
     *
     * @param itemName The name of the items to retrieve.
     * @return A collection of items with the specified name, or an empty collection if none are found.
     */
    public Collection<Item> getItemsByName(String itemName) {
        return items.containsKey(itemName) ? new TreeSet<>(items.get(itemName)) : Collections.emptySet();
    }

    /**
     * Retrieves an item by its name and optional expiry date.
     *
     * @param itemName   The name of the item.
     * @param expiryDate An optional expiry date. If provided, it will look for an item with this expiry date.
     * @return The item with the specified name and expiry date, or null if not found.
     */
    public Item getItemByNameAndExpiry(String itemName, Optional<LocalDate> expiryDate) {
        TreeSet<Item> itemSet = items.get(itemName);
        if (itemSet == null) {
            return null;
        }

        for (Item item : itemSet) {
            if (expiryDate.isPresent()) {
                if (item.getExpiryDate().equals(expiryDate)) {
                    return item;
                }
            } else if (item.getExpiryDate().isEmpty()) {
                return item;
            }
        }
        return null;
    }

    /**
     * Returns a flat list of all items in the inventory, for use in external classes like Visualizer.
     *
     * @return An ArrayList containing all items in the inventory.
     */
    public ArrayList<Item> getItemsAsArrayList() {
        return new ArrayList<>(getAllItems());
    }


    /**
     * Returns the set of items with the given name.
     *
     * @param itemName The name of the item to retrieve.
     * @return A TreeSet of items with the given name, or null if the item does not exist.
     */
    public TreeSet<Item> get(String itemName) {
        return items.getOrDefault(itemName, new TreeSet<>());
    }
}
