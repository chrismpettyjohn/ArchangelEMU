package com.us.archangel.gang.repository;

import com.us.archangel.gang.entity.GangEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class GangRepository {

    private static GangRepository instance;

    public static GangRepository getInstance(SessionFactory sessionFactory) {
        if (instance == null) {
            instance = new GangRepository(sessionFactory);
        }
        return instance;
    }

    public static GangRepository getInstance() {
        if (instance == null) {
            throw new RuntimeException("GangRepository has not been initialized");
        }
        return instance;
    }

    private final SessionFactory sessionFactory;

    private GangRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(GangEntity gang) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(gang);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void updateById(int id, GangEntity updatedGang) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            GangEntity gang = session.get(GangEntity.class, id);
            if (gang != null) {
                gang.setDisplayName(updatedGang.getDisplayName());
                gang.setUserId(updatedGang.getUserId());
                gang.setRoomId(updatedGang.getRoomId());
                session.update(gang);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public GangEntity getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(GangEntity.class, id);
        }
    }

    public List<GangEntity> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from GangEntity", GangEntity.class).list();  // Query simplified
        }
    }

    public List<GangEntity> findManyByDisplayName(String displayName) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from GangEntity where displayName ILIKE :displayName", GangEntity.class)
                    .setParameter("displayName", "%" + displayName + "%")
                    .list();
        }
    }

    public void deleteById(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            GangEntity gang = session.get(GangEntity.class, id);
            if (gang != null) {
                session.delete(gang);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
