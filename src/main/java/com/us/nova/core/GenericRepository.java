package com.us.nova.core;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public class GenericRepository<Entity> implements IGenericRepository<Entity> {

    private final Class<Entity> entityType;
    protected final SessionFactory sessionFactory;

    public GenericRepository(Class<Entity> entityType) {
        this.entityType = entityType;
        this.sessionFactory = DatabaseConfig.getSessionFactory();
    }

    @Override
    public Entity create(Entity entity) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Failed to create entity", e);
        }
        return entity;
    }

    @Override
    public void updateById(int id, Entity updatedEntity) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Entity entity = session.get(entityType, id);
            if (entity != null) {
                session.merge(updatedEntity);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Entity getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(entityType, id);
        }
    }

    @Override
    public List<Entity> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from " + entityType.getSimpleName(), entityType).list();
        }
    }

    @Override
    public void deleteById(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Entity entity = session.get(entityType, id);
            if (entity != null) {
                session.delete(entity);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
