package com.us.archangel.player.repository;

import com.us.archangel.player.entity.PlayerEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class PlayerRepository {

    private static PlayerRepository instance;

    public static PlayerRepository getInstance(SessionFactory sessionFactory) {
        if (instance == null) {
            instance = new PlayerRepository(sessionFactory);
        }
        return instance;
    }

    public static PlayerRepository getInstance() {
        if (instance == null) {
            throw new RuntimeException("PlayerRepository has not been initialized");
        }
        return instance;
    }

    private final SessionFactory sessionFactory;

    private PlayerRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(PlayerEntity player) {
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

    public void updateById(int id, PlayerEntity updatedPlayer) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            PlayerEntity player = session.get(PlayerEntity.class, id);
            if (player != null) {;
                player.setUserId(updatedPlayer.getUserId());
                session.update(player);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public PlayerEntity getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(PlayerEntity.class, id);
        }
    }

    @SuppressWarnings("unchecked")
    public List<PlayerEntity> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from PlayerEntity", PlayerEntity.class).list();  // Query simplified
        }
    }

    public void deleteById(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            PlayerEntity player = session.get(PlayerEntity.class, id);
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
