package com.us.archangel.sanction.repository;

import com.us.archangel.sanction.entity.SanctionEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class SanctionRepository {

    private static SanctionRepository instance;

    public static SanctionRepository getInstance(SessionFactory sessionFactory) {
        if (instance == null) {
            instance = new SanctionRepository(sessionFactory);
        }
        return instance;
    }

    public static SanctionRepository getInstance() {
        if (instance == null) {
            throw new RuntimeException("SanctionRepository has not been initialized");
        }
        return instance;
    }

    private final SessionFactory sessionFactory;

    private SanctionRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(SanctionEntity corp) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(corp);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void updateById(int id, SanctionEntity updatedPenalty) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            SanctionEntity corp = session.get(SanctionEntity.class, id);
            if (corp != null) {
                corp.setDisplayName(updatedPenalty.getDisplayName());
                corp.setDescription(updatedPenalty.getDescription());
                corp.setIsCriminal(updatedPenalty.getIsCriminal());
                corp.setCriminalSentenceInSeconds(updatedPenalty.getCriminalSentenceInSeconds());
                corp.setIsCivil(updatedPenalty.getIsCivil());
                corp.setCivilFineInCredits(updatedPenalty.getCivilFineInCredits());
                session.update(corp);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public SanctionEntity getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(SanctionEntity.class, id);
        }
    }

    @SuppressWarnings("unchecked")
    public List<SanctionEntity> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from SanctionEntity", SanctionEntity.class).list();  // Query simplified
        }
    }

    public void deleteById(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            SanctionEntity corp = session.get(SanctionEntity.class, id);
            if (corp != null) {
                session.delete(corp);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
