package com.us.archangel.corp.repository;

import com.us.archangel.corp.entity.CorpEntity;
import com.us.archangel.corp.enums.CorpIndustry;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class CorpRepository {

    private static CorpRepository instance;

    public static CorpRepository getInstance(SessionFactory sessionFactory) {
        if (instance == null) {
            instance = new CorpRepository(sessionFactory);
        }
        return instance;
    }

    public static CorpRepository getInstance() {
        if (instance == null) {
            throw new RuntimeException("CorpRepository has not been initialized");
        }
        return instance;
    }

    private final SessionFactory sessionFactory;

    private CorpRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(CorpEntity corp) {
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

    public void updateById(int id, CorpEntity updatedCorp) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            CorpEntity corp = session.get(CorpEntity.class, id);
            if (corp != null) {
                corp.setSector(updatedCorp.getSector());
                corp.setIndustry(updatedCorp.getIndustry());
                corp.setDisplayName(updatedCorp.getDisplayName());
                corp.setUserId(updatedCorp.getUserId());
                corp.setRoomId(updatedCorp.getRoomId());
                session.update(corp);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public CorpEntity getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(CorpEntity.class, id);
        }
    }

    public List<CorpEntity> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from CorpEntity", CorpEntity.class).list();  // Query simplified
        }
    }

    public List<CorpEntity> findManyByDisplayName(String displayName) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from CorpEntity where displayName ILIKE :displayName", CorpEntity.class)
                    .setParameter("displayName", "%" + displayName + "%")
                    .list();
        }
    }

    public List<CorpEntity> findManyByIndustry(CorpIndustry industry) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from CorpEntity where industry = :industry", CorpEntity.class)
                    .setParameter("industry", industry)
                    .list();
        }
    }

    public void deleteById(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            CorpEntity corp = session.get(CorpEntity.class, id);
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
