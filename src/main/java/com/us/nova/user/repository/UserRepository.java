package com.us.nova.user.repository;

import com.us.nova.user.entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserRepository {

    private static UserRepository instance;

    public static UserRepository getInstance(SessionFactory sessionFactory) {
        if (instance == null) {
            instance = new UserRepository(sessionFactory);
        }
        return instance;
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            throw new RuntimeException("UserRepository has not been initialized");
        }
        return instance;
    }

    private final SessionFactory sessionFactory;

    private UserRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(UserEntity user) {
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

    public void updateById(int id, UserEntity updatedUser) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            UserEntity user = session.get(UserEntity.class, id);
            if (user != null) {
                session.update(user);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public UserEntity getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(UserEntity.class, id);
        }
    }

    @SuppressWarnings("unchecked")
    public List<UserEntity> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from UserEntity", UserEntity.class).list();  // Query simplified
        }
    }

    public void deleteById(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            UserEntity user = session.get(UserEntity.class, id);
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
