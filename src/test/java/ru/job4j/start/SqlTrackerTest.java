package ru.job4j.start;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.models.ConnectionRollback;
import ru.job4j.models.Item;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SqlTrackerTest {

    public Connection init() {
        try (InputStream in = SqlTracker.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            return DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    public void createItem() throws Exception {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            Item itemOne = new Item("itemOne");
            tracker.add(itemOne);
            Item result = tracker.findById(itemOne.getId());
            assertThat(result.getName(), is("itemOne"));
        }
    }

    @Test
    public void whenReplaceItemThenTrue() {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            Item itemOne = new Item("itemOne");
            tracker.add(itemOne);
            Item item = new Item("new_item");
            Assert.assertTrue(tracker.replace((itemOne.getId()), item));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenDeleteThenTrue() {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            Item itemOne = new Item("itemOne");
            tracker.add(itemOne);
            Assert.assertTrue(tracker.delete((itemOne.getId())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenFindAllItemsThenNewSize() {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            List<Item> items;
            Item itemOne = new Item("itemOne");
            Item itemTwo = new Item("itemTwo");
            tracker.add(itemOne);
            tracker.add(itemTwo);
            items = tracker.findAll();
            assertThat(items.size(), is(2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenFindByNameThenGetListItems() {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            Item itemOne = new Item("itemOne");
            Item itemTwo = new Item("itemTwo");
            Item itemThree = new Item("itemTwo");
            tracker.add(itemOne);
            tracker.add(itemTwo);
            tracker.add(itemThree);
            List<Item> result = tracker.findByName("itemTwo");
            List<Item> expected = Arrays.asList(itemTwo, itemThree);
            assertThat(result, is(expected));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenFindByIdThenGetItem()  {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            Item itemOne = new Item("itemOne");
            Item itemTwo = new Item("itemTwo");
            Item itemThree = new Item("itemTwo");
            tracker.add(itemOne);
            tracker.add(itemTwo);
            tracker.add(itemThree);
            Item result = tracker.findById(itemTwo.getId());
            assertThat(result, is(itemTwo));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}