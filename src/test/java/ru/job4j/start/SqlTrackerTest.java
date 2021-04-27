package ru.job4j.start;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.models.Item;


import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SqlTrackerTest {
   private SqlTracker tracker;

    @Before
    public void initSqlTracker() {
        tracker = new SqlTracker();
    }

    @Before
    public void cleanTable() throws SQLException {
        tracker.deleteAll();
    }

    @Test
    public void whenAddItem() {
        Item itemOne = new Item("itemOne");
        tracker.add(itemOne);
        Item result = tracker.findById(itemOne.getId());
        assertThat(result.getName(), is("itemOne"));
    }

    @Test
    public void whenReplaceItemThenTrue() {
        Item itemOne = new Item("itemOne");
        tracker.add(itemOne);
        Item item = new Item("new_item");
        Assert.assertTrue(tracker.replace((itemOne.getId()), item));
    }

    @Test
    public void whenDeleteThenTrue() {
        Item itemOne = new Item("itemOne");
        tracker.add(itemOne);
        Assert.assertTrue(tracker.delete((itemOne.getId())));
    }

    @Test
    public void whenFindAllItemsThenNewSize() {
        List<Item> items;
        Item itemOne = new Item("itemOne");
        Item itemTwo = new Item("itemTwo");
        tracker.add(itemOne);
        tracker.add(itemTwo);
        items = tracker.findAll();
        assertThat(items.size(), is(2));
    }

    @Test
    public void whenFindByNameThenGetListItems() {
        Item itemOne = new Item("itemOne");
        Item itemTwo = new Item("itemTwo");
        Item itemThree = new Item("itemTwo");
        tracker.add(itemOne);
        tracker.add(itemTwo);
        tracker.add(itemThree);
        List<Item> result = tracker.findByName("itemTwo");
        List<Item> expected = Arrays.asList(itemTwo, itemThree);
        assertThat(result, is(expected));
    }

    @Test
    public void whenFindByIdThenGetItem() {
        Item itemOne = new Item("itemOne");
        Item itemTwo = new Item("itemTwo");
        Item itemThree = new Item("itemTwo");
        tracker.add(itemOne);
        tracker.add(itemTwo);
        tracker.add(itemThree);
        Item result = tracker.findById(itemTwo.getId());
        assertThat(result, is(itemTwo));
    }
}