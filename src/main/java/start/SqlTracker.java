package ru.job4j.start;

import ru.job4j.models.Item;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SqlTracker implements Store {
    private Connection cn;

    public SqlTracker() {
        init();
    }

    @Override
    public void init() {
        try (InputStream in = SqlTracker.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            cn = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Item add(Item item)  {
        try (PreparedStatement statement = cn.prepareStatement("insert into items(name) values (?)",
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getName());
            statement.execute();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int value = generatedKeys.getInt(1);
                    String id = Integer.toString(value);
                    item.setId(id);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return item;
    }

    @Override
    public boolean replace(String id, Item item)  {
        boolean result = false;
        try (PreparedStatement statement = cn.prepareStatement("update items set name = ? where id = ?")) {
            statement.setString(1, item.getName());
            statement.setInt(2, Integer.parseInt(id));
            result = statement.executeUpdate() == 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean delete(String id)  {
        boolean result = false;
        try (PreparedStatement statement = cn.prepareStatement("delete from items where id = ?")) {
            statement.setInt(1, Integer.parseInt(id));
            result = statement.executeUpdate() == 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Item> findAll() {
        List<Item> items = new ArrayList<>();
        try (PreparedStatement statement = cn.prepareStatement("select * from items")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Item item = new Item(resultSet.getString("name"));
                    int value = resultSet.getInt("id");
                    String id = Integer.toString(value);
                    item.setId(id);
                    items.add(item);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return items;
    }

    @Override
    public List<Item> findByName(String key)  {
        List<Item> items = new ArrayList<>();
        try (PreparedStatement statement = cn.prepareStatement("select * from items where name = ?")) {
            statement.setString(1, key);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Item item = new Item(key);
                    int value = resultSet.getInt("id");
                    String id = Integer.toString(value);
                    item.setId(id);
                    items.add(item);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return items;
    }

    @Override
    public Item findById(String id)  {
        Item item = null;
        try (PreparedStatement statement = cn.prepareStatement("select * from items where id = ?")) {
            statement.setInt(1, Integer.parseInt(id));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String num = resultSet.getString("id");
                    item = new Item(resultSet.getString("name"));
                    item.setId(num);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return item;
    }

    public void deleteAll() throws SQLException {
        try (PreparedStatement statement = cn.prepareStatement("delete  from items")) {
            statement.execute();
        }
    }

    @Override
    public void close() throws Exception {
        if (cn != null) {
            cn.close();
        }
    }
}
