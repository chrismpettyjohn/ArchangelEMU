package com.us.nova.betacode.repository;

import com.us.nova.betacode.entity.BetaCodeEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class BetaCodeRepository {

    private static BetaCodeRepository instance;

    public static BetaCodeRepository getInstance(SessionFactory sessionFactory) {
        if (instance == null) {
            instance = new BetaCodeRepository(sessionFactory);
        }
        return instance;
    }

    public static BetaCodeRepository getInstance() {
        if (instance == null) {
            throw new RuntimeException("Beta Code Repository has not been initialized");
        }
        return instance;
    }

    private final SessionFactory sessionFactory;

    private BetaCodeRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(BetaCodeEntity betaCode) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(betaCode);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void updateById(int id, BetaCodeEntity updatedBetaCode) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            BetaCodeEntity betaCode = session.get(BetaCodeEntity.class, id);
            if (betaCode != null) {
                session.update(betaCode);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public BetaCodeEntity getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(BetaCodeEntity.class, id);
        }
    }

    @SuppressWarnings("unchecked")
    public List<BetaCodeEntity> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from BetaCodeEntity", BetaCodeEntity.class).list();  // Query simplified
        }
    }

    public void deleteById(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            BetaCodeEntity betaCode = session.get(BetaCodeEntity.class, id);
            if (betaCode != null) {
                session.delete(betaCode);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
