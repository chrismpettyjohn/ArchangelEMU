package com.us.archangel.player.repository;

import com.us.archangel.player.entity.PlayerBankAccountEntity;
import com.us.archangel.player.entity.PlayerBankAccountEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class PlayerBankAccountRepository {

    private static PlayerBankAccountRepository instance;

    public static PlayerBankAccountRepository getInstance(SessionFactory sessionFactory) {
        if (instance == null) {
            instance = new PlayerBankAccountRepository(sessionFactory);
        }
        return instance;
    }

    public static PlayerBankAccountRepository getInstance() {
        if (instance == null) {
            throw new RuntimeException("PlayerBankAccountRepository has not been initialized");
        }
        return instance;
    }

    private final SessionFactory sessionFactory;

    private PlayerBankAccountRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(PlayerBankAccountEntity player) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(player);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void updateById(int id, PlayerBankAccountEntity updatedPlayerBankAccount) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            PlayerBankAccountEntity player = session.get(PlayerBankAccountEntity.class, id);
            if (player != null) {;
                player.setUserId(updatedPlayerBankAccount.getUserId());
                session.update(player);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public PlayerBankAccountEntity getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(PlayerBankAccountEntity.class, id);
        }
    }

    public PlayerBankAccountEntity getByUserId(int userId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from PlayerBankAccountEntity where userId = :userId", PlayerBankAccountEntity.class)
                    .setParameter("userId", userId)
                    .uniqueResult();
        }
    }

    @SuppressWarnings("unchecked")
    public List<PlayerBankAccountEntity> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from PlayerBankAccountEntity", PlayerBankAccountEntity.class).list();  // Query simplified
        }
    }

    public void deleteById(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            PlayerBankAccountEntity player = session.get(PlayerBankAccountEntity.class, id);
            if (player != null) {
                session.delete(player);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
