package ru.job4j.start;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.models.Item;

import java.util.List;

public class HbmTracker implements Store, AutoCloseable {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata()
            .buildSessionFactory();
    private Session session;

    @Override
    public void init() {
        session = sf.openSession();
    }

    @Override
    public Item add(Item item) {
        session.beginTransaction();
        session.save(item);
        session.getTransaction()
               .commit();
        session.clear();
        return item;
    }

    @Override
    public boolean replace(String id, Item item) {
        boolean result = false;
        try {
            session.beginTransaction();
            Query query = session.createQuery("update Item set name = :fName where id = :fId");
            query.setParameter("fName", item.getName());
            query.setParameter("fId", id);
            result = query.executeUpdate() == 1;
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        session.close();
        return result;
    }

    @Override
    public boolean delete(String id) {
        boolean result = false;
        try {
            session.beginTransaction();
            Query query = session.createQuery("delete Item where id = :fId");
            query.setParameter("fId", id);
            result = query.executeUpdate() == 1;
            session.getTransaction()
                   .commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
            session.close();
            return result;
        }

        @Override
        public List<Item> findAll() {
            session.beginTransaction();
            List items = session.createQuery("from Item")
                                .list();
            session.getTransaction()
                   .commit();
            session.close();
            return items;
        }

        @Override
        public List<Item> findByName(String key) {
            session.beginTransaction();
            Query<Item> query = session.createQuery("from Item as i where i.name = :fName", Item.class);
            query.setParameter("fName", key);
            query.executeUpdate();
            session.close();
            return query.getResultList();
        }

        @Override
        public Item findById(String id) {
            session.beginTransaction();
            Item result = session.get(Item.class, id);
            session.getTransaction()
                   .commit();
            session.close();
            return result;
        }

        @Override
        public void close() throws Exception {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
