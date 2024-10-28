package com.us.nova.user.repository;

import com.us.nova.user.entity.UserSSOEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserSSORepository {

    private static UserSSORepository instance;

    public static UserSSORepository getInstance(SessionFactory sessionFactory) {
        if (instance == null) {
            instance = new UserSSORepository(sessionFactory);
        }
        return instance;
    }

    public static UserSSORepository getInstance() {
        if (instance == null) {
            throw new RuntimeException("UserSSORepository has not been initialized");
        }
        return instance;
    }

    private final SessionFactory sessionFactory;

    private UserSSORepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(UserSSOEntity user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void updateById(int id, UserSSOEntity updatedUser) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            UserSSOEntity user = session.get(UserSSOEntity.class, id);
            if (user != null) {
                session.update(user);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public UserSSOEntity getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(UserSSOEntity.class, id);
        }
    }

    @SuppressWarnings("unchecked")
    public List<UserSSOEntity> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from UserSSOEntity ", UserSSOEntity.class).list();  // Query simplified
        }
    }

    public void deleteById(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            UserSSOEntity user = session.get(UserSSOEntity.class, id);
            if (user != null) {
                session.delete(user);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
