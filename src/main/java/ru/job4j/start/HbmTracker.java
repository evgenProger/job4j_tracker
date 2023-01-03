package ru.job4j.start;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.models.Item;

import java.util.List;

public class HbmTracker implements Store, AutoCloseable {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();
    private Session session;

    @Override
    public void init() {
        session = sf.openSession();
    }

    @Override
    public Item add(Item item) {
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.clear();
        return item;
    }

    @Override
    public boolean replace(String id, Item item) {
        session.beginTransaction();
        session.update(id, item);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String id) {
        session.beginTransaction();
        session.delete(findById(id));
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public List<Item> findAll() {
        session.beginTransaction();
        List items = session.createQuery("from ru.job4j.models.Item").list();
        session.getTransaction().commit();
        session.close();
        return items;
    }

    @Override
    public List<Item> findByName(String key) {
        session.beginTransaction();
        List items = session.createQuery("from Item as i where i.name = 'key'", Item.class).list();
        session.getTransaction().commit();
        session.close();
        return items;
    }

    @Override
    public Item findById(String id) {
       session.beginTransaction();
       Item result = session.get(Item.class, id);
       session.getTransaction().commit();
       session.close();
        return result;
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
