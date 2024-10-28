package com.us.archangel.crime.repository;

import com.us.archangel.crime.entity.CrimeEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class CrimeRepository {

    private static CrimeRepository instance;

    public static CrimeRepository getInstance(SessionFactory sessionFactory) {
        if (instance == null) {
            instance = new CrimeRepository(sessionFactory);
        }
        return instance;
    }

    public static CrimeRepository getInstance() {
        if (instance == null) {
            throw new RuntimeException("CrimeRepository has not been initialized");
        }
        return instance;
    }

    private final SessionFactory sessionFactory;

    private CrimeRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(CrimeEntity crime) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(crime);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void updateById(int id, CrimeEntity updatedCrime) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            CrimeEntity crime = session.get(CrimeEntity.class, id);
            if (crime != null) {
                crime.setDisplayName(updatedCrime.getDisplayName());
                crime.setDescription(updatedCrime.getDescription());
                crime.setJailTimeSeconds(updatedCrime.getJailTimeSeconds());
                session.update(crime);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public CrimeEntity getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(CrimeEntity.class, id);
        }
    }

    @SuppressWarnings("unchecked")
    public List<CrimeEntity> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from CrimeEntity", CrimeEntity.class).list();  // Query simplified
        }
    }

    public void deleteById(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            CrimeEntity crime = session.get(CrimeEntity.class, id);
            if (crime != null) {
                session.delete(crime);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
