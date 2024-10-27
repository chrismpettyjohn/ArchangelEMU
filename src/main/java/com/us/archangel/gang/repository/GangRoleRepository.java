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
                gangRole.setGangId(updatedGangRole.getGangId());
                gangRole.setOrderId(updatedGangRole.getOrderId());
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

    public GangRoleEntity getByGangIdAndOrderId(int gangId, int orderId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from GangRoleEntity where gangId = :gangId and orderId = :orderId", GangRoleEntity.class)
                    .setParameter("gangId", gangId)
                    .setParameter("orderId", orderId)
                    .uniqueResult();
        }
    }

    public List<GangRoleEntity> getByGangId(int gangId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from GangRoleEntity where gangId = :gangId", GangRoleEntity.class)
                    .setParameter("gangId", gangId)
                    .list();
        }
    }

    public List<GangRoleEntity> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from GangRoleEntity", GangRoleEntity.class).list();
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
