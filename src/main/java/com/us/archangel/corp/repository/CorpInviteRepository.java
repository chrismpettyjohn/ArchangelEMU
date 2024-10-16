package com.us.archangel.corp.repository;

import com.us.archangel.corp.entity.CorpInviteEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class CorpInviteRepository {

    private static CorpInviteRepository instance;

    public static CorpInviteRepository getInstance(SessionFactory sessionFactory) {
        if (instance == null) {
            instance = new CorpInviteRepository(sessionFactory);
        }
        return instance;
    }

    public static CorpInviteRepository getInstance() {
        if (instance == null) {
            throw new RuntimeException("CorpInviteRepository has not been initialized");
        }
        return instance;
    }

    private final SessionFactory sessionFactory;

    private CorpInviteRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(CorpInviteEntity corpRole) {
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

    public void updateById(int id, CorpInviteEntity updatedCorpInvite) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            CorpInviteEntity corpRole = session.get(CorpInviteEntity.class, id);
            if (corpRole != null) {
                corpRole.setCorpId(updatedCorpInvite.getCorpId());
                corpRole.setCorpRoleId(updatedCorpInvite.getCorpRoleId());
                corpRole.setUserId(updatedCorpInvite.getUserId());
                session.update(corpRole);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public CorpInviteEntity getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(CorpInviteEntity.class, id);
        }
    }

    public CorpInviteEntity getByCorpAndUserId(int corpId, int userId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from CorpInviteEntity where corpId = :corpId AND userId = :userId", CorpInviteEntity.class)
                    .setParameter("corpId", corpId)
                    .setParameter("userId", userId)
                    .getSingleResult();
        }
    }

    public List<CorpInviteEntity> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from CorpInviteEntity", CorpInviteEntity.class).list();
        }
    }

    public void deleteById(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            CorpInviteEntity corpRole = session.get(CorpInviteEntity.class, id);
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
