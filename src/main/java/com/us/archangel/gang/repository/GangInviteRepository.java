package com.us.archangel.gang.repository;

import com.us.archangel.gang.entity.GangInviteEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class GangInviteRepository {

    private static GangInviteRepository instance;

    public static GangInviteRepository getInstance(SessionFactory sessionFactory) {
        if (instance == null) {
            instance = new GangInviteRepository(sessionFactory);
        }
        return instance;
    }

    public static GangInviteRepository getInstance() {
        if (instance == null) {
            throw new RuntimeException("GangInviteRepository has not been initialized");
        }
        return instance;
    }

    private final SessionFactory sessionFactory;

    private GangInviteRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(GangInviteEntity gangRole) {
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

    public void updateById(int id, GangInviteEntity updatedGangInvite) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            GangInviteEntity gangRole = session.get(GangInviteEntity.class, id);
            if (gangRole != null) {
                gangRole.setGangId(updatedGangInvite.getGangId());
                gangRole.setGangRoleId(updatedGangInvite.getGangRoleId());
                gangRole.setUserId(updatedGangInvite.getUserId());
                session.update(gangRole);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public GangInviteEntity getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(GangInviteEntity.class, id);
        }
    }

    public GangInviteEntity getByGangAndUserId(int gangId, int userId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from GangInviteEntity where gangId = :gangId AND userId = :userId", GangInviteEntity.class)
                    .setParameter("gangId", gangId)
                    .setParameter("userId", userId)
                    .getSingleResult();
        }
    }

    public List<GangInviteEntity> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from GangInviteEntity", GangInviteEntity.class).list();
        }
    }

    public void deleteById(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            GangInviteEntity gangRole = session.get(GangInviteEntity.class, id);
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
