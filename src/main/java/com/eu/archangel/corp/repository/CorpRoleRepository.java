package com.eu.archangel.corp.repository;

import com.eu.archangel.corp.entity.CorpRoleEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class CorpRoleRepository {

    private static CorpRoleRepository instance;

    public static CorpRoleRepository getInstance(SessionFactory sessionFactory) {
        if (instance == null) {
            instance = new CorpRoleRepository(sessionFactory);
        }
        return instance;
    }

    public static CorpRoleRepository getInstance() {
        if (instance == null) {
            throw new RuntimeException("CorpRoleRepository has not been initialized");
        }
        return instance;
    }

    private final SessionFactory sessionFactory;

    private CorpRoleRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(CorpRoleEntity corpRole) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(corpRole);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void updateById(int id, CorpRoleEntity updatedCorpRole) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            CorpRoleEntity corpRole = session.get(CorpRoleEntity.class, id);
            if (corpRole != null) {
                corpRole.setName(updatedCorpRole.getName());
                corpRole.setCorpID(updatedCorpRole.getCorpID());
                corpRole.setOrderID(updatedCorpRole.getOrderID());
                corpRole.setMotto(updatedCorpRole.getMotto());
                corpRole.setSalary(updatedCorpRole.getSalary());
                corpRole.setMaleFigure(updatedCorpRole.getMaleFigure());
                corpRole.setFemaleFigure(updatedCorpRole.getFemaleFigure());
                corpRole.setCanHire(updatedCorpRole.isCanHire());
                corpRole.setCanFire(updatedCorpRole.isCanFire());
                corpRole.setCanPromote(updatedCorpRole.isCanPromote());
                corpRole.setCanDemote(updatedCorpRole.isCanDemote());
                corpRole.setCanWorkAnywhere(updatedCorpRole.isCanWorkAnywhere());
                session.update(corpRole);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public CorpRoleEntity getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(CorpRoleEntity.class, id);
        }
    }

    @SuppressWarnings("unchecked")
    public List<CorpRoleEntity> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from CorpRoleEntity").list();
        }
    }

    public void deleteById(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            CorpRoleEntity corpRole = session.get(CorpRoleEntity.class, id);
            if (corpRole != null) {
                session.delete(corpRole);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
