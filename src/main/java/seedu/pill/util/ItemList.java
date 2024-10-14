package seedu.pill.util;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represents a list of items and provides methods to add, delete, list, and edit items.
 */
public class ItemList implements Iterable<Item> {
    private ArrayList<Item> items;

    /**
     * Constructor for ItemList.
     * Initializes the internal ArrayList to store items.
     */
    public ItemList() {
        this.items = new ArrayList<>();
    }

    /**
     * Constructor for ItemList.
     * Constructs an ItemList based on passed ArrayList of items
     * @param items The list of items to be constructed into an ItemList
     */
    public ItemList(ArrayList<Item> items) {
        this.items = items;
    }

    /**
     * Returns an iterator over the tasks in the TaskList.
     * This allows for iteration over the tasks using an enhanced for-loop.
     *
     * @return an Iterator of Task objects.
     */
    @Override
    public Iterator<Item> iterator() {
        return items.iterator();
    }

    /**
     * Adds a new item to the list.
     *
     * @param name     The name of the item.
     * @param quantity The quantity of the item.
     */
    public void addItem(String name, int quantity) {
        if (name == null || name.trim().isEmpty() || quantity <= 0) {
            System.out.println("Invalid item name or quantity.");
            return;
        }
        items.add(new Item(name, quantity));  // Add the item to the list.
        System.out.println("Added the following item to the inventory: \n"
                           + name + ": " + quantity + " in stock");
    }

    /**
     * Deletes an item from the list by its name.
     *
     * @param name The name of the item to be deleted.
     */
    public void deleteItem(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Invalid item name.");
            return;
        }
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getName().equalsIgnoreCase(name)) {
                System.out.println("Deleted the following item from the inventory: \n"
                                   + (i + 1) + ". " + items.get(i).getName() + ": " + items.get(i).getQuantity());
                items.remove(i);
                return;
            }
        }
        System.out.println("Item not found: " + name);
    }

    /**
     * Edits an item by its name.
     *
     * @param name     The name of the item to be edited.
     * @param quantity The new quantity for the item.
     */
    public void editItem(String name, int quantity) {
        if (name == null || name.trim().isEmpty() || quantity <= 0) {
            System.out.println("Invalid item name or quantity.");
            return;
        }

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if (item.getName().equalsIgnoreCase(name)) {
                item.setQuantity(quantity);
                System.out.println("Edited item: " + name + " (" + quantity + " in stock)");
                return;
            }
        }

        System.out.println("Item not found: " + name);
    }

    /**
     * Lists all the items in the inventory.
     */
    public void listItems() {
        if (items.isEmpty()) {
            System.out.println("The inventory is empty.");
            return;
        }
        System.out.println("Listing all items:");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i).getName() + ": "
                               + items.get(i).getQuantity() + " in stock");
        }
    }

    /**
     * Finds an item in the list.
     *
     * @param itemName     The name of the item.
     */
    public ItemList findItem(String itemName) {
        ArrayList<Item> foundItems = new ArrayList<Item>();
        if (itemName == null || itemName.trim().isEmpty()) {
            return new ItemList(foundItems);
        }
        for (Item item : items) {
            if (item.getName().contains(itemName)) {
                foundItems.add(item);
            }
        }
        return new ItemList(foundItems);
    }
}
