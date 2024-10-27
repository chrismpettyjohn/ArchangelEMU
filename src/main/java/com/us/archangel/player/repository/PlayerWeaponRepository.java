package com.us.archangel.player.repository;

import com.us.archangel.player.entity.PlayerWeaponEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class PlayerWeaponRepository {

    private static PlayerWeaponRepository instance;

    public static PlayerWeaponRepository getInstance(SessionFactory sessionFactory) {
        if (instance == null) {
            instance = new PlayerWeaponRepository(sessionFactory);
        }
        return instance;
    }

    public static PlayerWeaponRepository getInstance() {
        if (instance == null) {
            throw new RuntimeException("PlayerWeaponRepository has not been initialized");
        }
        return instance;
    }

    private final SessionFactory sessionFactory;

    private PlayerWeaponRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(PlayerWeaponEntity player) {
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

    public void updateById(int id, PlayerWeaponEntity updatedPlayerWeapon) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            PlayerWeaponEntity player = session.get(PlayerWeaponEntity.class, id);
            if (player != null) {;
                player.setPlayerId(updatedPlayerWeapon.getPlayerId());
                session.update(player);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public PlayerWeaponEntity getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(PlayerWeaponEntity.class, id);
        }
    }


    public List<PlayerWeaponEntity> getByPlayerId(int playerId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from PlayerWeaponEntity where playerId = :playerId", PlayerWeaponEntity.class)
                    .setParameter("playerId", playerId)
                    .list();
        }
    }

    public List<PlayerWeaponEntity> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from PlayerWeaponEntity", PlayerWeaponEntity.class).list();  // Query simplified
        }
    }

    public void deleteById(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            PlayerWeaponEntity player = session.get(PlayerWeaponEntity.class, id);
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
