package com.us.nova.bugreport.repository;

import com.us.nova.bugreport.entity.BugReportEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class BugReportRepository {

    private static BugReportRepository instance;

    public static BugReportRepository getInstance(SessionFactory sessionFactory) {
        if (instance == null) {
            instance = new BugReportRepository(sessionFactory);
        }
        return instance;
    }

    public static BugReportRepository getInstance() {
        if (instance == null) {
            throw new RuntimeException("Bug Report Repository has not been initialized");
        }
        return instance;
    }

    private final SessionFactory sessionFactory;

    private BugReportRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(BugReportEntity bugReport) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(bugReport);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void updateById(int id, BugReportEntity updatedBugReport) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            BugReportEntity bugReport = session.get(BugReportEntity.class, id);
            if (bugReport != null) {
                session.update(bugReport);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public BugReportEntity getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(BugReportEntity.class, id);
        }
    }

    @SuppressWarnings("unchecked")
    public List<BugReportEntity> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from BugReportEntity", BugReportEntity.class).list();  // Query simplified
        }
    }

    public void deleteById(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            BugReportEntity bugReport = session.get(BugReportEntity.class, id);
            if (bugReport != null) {
                session.delete(bugReport);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
