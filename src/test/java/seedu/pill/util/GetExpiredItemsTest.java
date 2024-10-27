package seedu.pill.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetExpiredItemsTest {
    @Test
    public void getExpiredEmptyTest() {
        ItemMap items = new ItemMap();
        ItemMap expiredItems = items.getExpiringItems(LocalDate.now());
        assertTrue(expiredItems.isEmpty());
    }

    @Test
    public void getExpiredTestNoDate() {
        ItemMap items = new ItemMap();
        Item item1 = new Item("a", 5);
        items.addItem(item1);
        ItemMap expiredItems = items.getExpiringItems(LocalDate.now());
        assertTrue(expiredItems.isEmpty());
    }

    @Test
    public void getExpiredTestSimpleExpired() {
        Item item1 = new Item("a", 5, LocalDate.now().plusDays(-1));
        Item item2 = new Item("a", 2, LocalDate.now().plusDays(1));
        Item item3 = new Item("a", 3, LocalDate.now().plusDays(2));
        ItemMap items = new ItemMap();
        items.addItem(item1);
        items.addItem(item2);
        items.addItem(item3);
        ItemMap expectedItems = new ItemMap();
        expectedItems.addItem(item1);
        ItemMap expiredItems = items.getExpiringItems(LocalDate.now());
        assertEquals(expectedItems, expiredItems);
    }

    @Test
    public void getExpiredTestSimpleNotExpired() {
        Item item1 = new Item("a", 5, LocalDate.now().plusDays(1));
        Item item2 = new Item("a", 2, LocalDate.now().plusDays(2));
        Item item3 = new Item("a", 3, LocalDate.now().plusDays(3));
        ItemMap items = new ItemMap();
        items.addItem(item1);
        items.addItem(item2);
        items.addItem(item3);
        ItemMap expiredItems = items.getExpiringItems(LocalDate.now());
        assertTrue(expiredItems.isEmpty());
    }

    @Test
    public void getExpiredTestMixed() {
        Item item1 = new Item("a", 5, LocalDate.now().plusDays(-22));
        Item item2 = new Item("b", 6, LocalDate.now().plusDays(-2));
        Item item3 = new Item("c", 4, LocalDate.now().plusDays(3));
        Item item4 = new Item("a", 8, LocalDate.now().plusDays(4));
        Item item5 = new Item("b", 2, LocalDate.now().plusDays(5));
        ItemMap items = new ItemMap();
        items.addItem(item1);
        items.addItem(item2);
        items.addItem(item3);
        items.addItem(item4);
        items.addItem(item5);
        ItemMap expectedItems = new ItemMap();
        expectedItems.addItem(item1);
        expectedItems.addItem(item2);
        ItemMap expiredItems = items.getExpiringItems(LocalDate.now());
        assertEquals(expectedItems, expiredItems);
    }
}
