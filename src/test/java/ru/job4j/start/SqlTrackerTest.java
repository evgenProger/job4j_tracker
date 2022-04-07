package ru.job4j.start;


import org.junit.*;
import ru.job4j.models.Item;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SqlTrackerTest {

    static Connection connection;

    @BeforeClass
    public static void initConnection() {
        try (InputStream in = SqlTrackerTest.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            connection = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")

            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @AfterClass
    public static void closeConnection() throws SQLException {
        connection.close();
    }

    @After
    public void wipeTable() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("delete from items")) {
            statement.execute();
        }
    }


    @Test
    public void whenReplaceItemThenTrue() throws SQLException {
        SqlTracker tracker = new SqlTracker();
        Item itemOne = new Item("itemOne");
        tracker.add(itemOne);
        Item item = new Item("new_item");
        Assert.assertTrue(tracker.replace((itemOne.getId()), item));
    }

    @Test
    public void whenDeleteThenTrue() throws SQLException {
        SqlTracker tracker = new SqlTracker();
        Item itemOne = new Item("itemOne");
        tracker.add(itemOne);
        Assert.assertTrue(tracker.delete((itemOne.getId())));

    }

    @Test
    public void whenFindAllItemsThenNewSize() throws SQLException {
        SqlTracker tracker = new SqlTracker(connection);
        List<Item> items;
        int size = tracker.findAll().size();
        Item itemOne = new Item("itemOne");
        Item itemTwo = new Item("itemTwo");
        tracker.add(itemOne);
        tracker.add(itemTwo);
        items = tracker.findAll();
        assertThat(items.size(), is(size + 2));

    }

    @Test
    public void whenFindByNameThenGetListItems() throws SQLException {
        SqlTracker tracker = new SqlTracker(connection);
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
    public void whenFindByIdThenGetItem() throws SQLException {
        SqlTracker tracker = new SqlTracker(connection);
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