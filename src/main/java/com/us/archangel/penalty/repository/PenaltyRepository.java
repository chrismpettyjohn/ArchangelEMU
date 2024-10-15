package com.us.archangel.penalty.repository;

import com.us.archangel.penalty.entity.PenaltyEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class PenaltyRepository {

    private static PenaltyRepository instance;

    public static PenaltyRepository getInstance(SessionFactory sessionFactory) {
        if (instance == null) {
            instance = new PenaltyRepository(sessionFactory);
        }
        return instance;
    }

    public static PenaltyRepository getInstance() {
        if (instance == null) {
            throw new RuntimeException("PenaltyRepository has not been initialized");
        }
        return instance;
    }

    private final SessionFactory sessionFactory;

    private PenaltyRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(PenaltyEntity corp) {
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

    public void updateById(int id, PenaltyEntity updatedPenalty) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            PenaltyEntity corp = session.get(PenaltyEntity.class, id);
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

    public PenaltyEntity getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(PenaltyEntity.class, id);
        }
    }

    @SuppressWarnings("unchecked")
    public List<PenaltyEntity> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from PenaltyEntity", PenaltyEntity.class).list();  // Query simplified
        }
    }

    public void deleteById(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            PenaltyEntity corp = session.get(PenaltyEntity.class, id);
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
