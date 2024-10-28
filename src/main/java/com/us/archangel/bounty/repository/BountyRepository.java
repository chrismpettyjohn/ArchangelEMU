package com.us.archangel.bounty.repository;

import com.us.archangel.bounty.entity.BountyEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class BountyRepository {

    private static BountyRepository instance;

    public static BountyRepository getInstance(SessionFactory sessionFactory) {
        if (instance == null) {
            instance = new BountyRepository(sessionFactory);
        }
        return instance;
    }

    public static BountyRepository getInstance() {
        if (instance == null) {
            throw new RuntimeException("BountyRepository has not been initialized");
        }
        return instance;
    }

    private final SessionFactory sessionFactory;

    private BountyRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(BountyEntity bounty) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(bounty);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void updateById(int id, BountyEntity updatedBounty) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            BountyEntity bounty = session.get(BountyEntity.class, id);
            if (bounty != null) {
                bounty.setUserId(updatedBounty.getUserId());
                bounty.setCrimeId(updatedBounty.getCrimeId());
                session.update(bounty);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public BountyEntity getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(BountyEntity.class, id);
        }
    }

    public List<BountyEntity> getByUserId(int userId) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM BountyEntity WHERE userId = :userId";
            return session.createQuery(hql, BountyEntity.class)
                    .setParameter("userId", userId)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public List<BountyEntity> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from BountyEntity", BountyEntity.class).list();  // Query simplified
        }
    }

    public void deleteById(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            BountyEntity bounty = session.get(BountyEntity.class, id);
            if (bounty != null) {
                session.delete(bounty);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
