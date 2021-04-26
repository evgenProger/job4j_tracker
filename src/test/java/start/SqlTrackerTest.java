package ru.job4j.start;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.models.Item;

import java.sql.SQLException;
import java.util.ArrayList;
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
        Item item_one = new Item("item_one");
        tracker.add(item_one);
        Item result = tracker.findById(item_one.getId());
        assertThat(result.getName(), is("item_one"));
    }

    @Test
    public void whenReplaceItemThenTrue() {
        Item item_one = new Item("item_one");
        tracker.add(item_one);
        Item item = new Item("new_item");
        Assert.assertTrue(tracker.replace((item_one.getId()), item));
    }

    @Test
    public void whenDeleteThenTrue() {
        Item item_one = new Item("item_one");
        tracker.add(item_one);
        Assert.assertTrue(tracker.delete((item_one.getId())));
    }

    @Test
    public void whenFindAllItemsThenNewSize() {
        List<Item> items;
        Item item_one = new Item("item_one");
        Item item_two = new Item("item_two");
        tracker.add(item_one);
        tracker.add(item_two);
        items = tracker.findAll();
        assertThat(items.size(), is(2));
    }

    @Test
    public void whenFindByNameThenGetListItems() {
        Item item_one = new Item("item_one");
        Item item_two = new Item("item_two");
        Item item_three = new Item("item_two");
        tracker.add(item_one);
        tracker.add(item_two);
        tracker.add(item_three);
        List<Item> result = tracker.findByName("item_two");
        List<Item> expected = Arrays.asList(item_two, item_three);
        assertThat(result, is(expected));
    }

    @Test
    public void whenFindByIdThenGetItem() {
        Item item_one = new Item("item_one");
        Item item_two = new Item("item_two");
        Item item_three = new Item("item_two");
        tracker.add(item_one);
        tracker.add(item_two);
        tracker.add(item_three);
        Item result = tracker.findById(item_two.getId());
        assertThat(result, is(item_two));
    }
}