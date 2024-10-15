package com.us.archangel.gang.repository;

import com.us.archangel.gang.entity.GangRoleEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class GangRoleRepository {

    private static GangRoleRepository instance;

    public static GangRoleRepository getInstance(SessionFactory sessionFactory) {
        if (instance == null) {
            instance = new GangRoleRepository(sessionFactory);
        }
        return instance;
    }

    public static GangRoleRepository getInstance() {
        if (instance == null) {
            throw new RuntimeException("GangRoleRepository has not been initialized");
        }
        return instance;
    }

    private final SessionFactory sessionFactory;

    private GangRoleRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(GangRoleEntity gangRole) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(gangRole);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void updateById(int id, GangRoleEntity updatedGangRole) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            GangRoleEntity gangRole = session.get(GangRoleEntity.class, id);
            if (gangRole != null) {
                gangRole.setName(updatedGangRole.getName());
                gangRole.setGangID(updatedGangRole.getGangID());
                gangRole.setOrderID(updatedGangRole.getOrderID());
                session.update(gangRole);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public GangRoleEntity getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(GangRoleEntity.class, id);
        }
    }

    @SuppressWarnings("unchecked")
    public List<GangRoleEntity> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from GangRoleEntity").list();
        }
    }

    public void deleteById(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            GangRoleEntity gangRole = session.get(GangRoleEntity.class, id);
            if (gangRole != null) {
                session.delete(gangRole);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
