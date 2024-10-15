package com.us.archangel.player.repository;

import com.us.archangel.player.entity.PlayerSkillEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class PlayerSkillRepository {

    private static PlayerSkillRepository instance;

    public static PlayerSkillRepository getInstance(SessionFactory sessionFactory) {
        if (instance == null) {
            instance = new PlayerSkillRepository(sessionFactory);
        }
        return instance;
    }

    public static PlayerSkillRepository getInstance() {
        if (instance == null) {
            throw new RuntimeException("PlayerSkillRepository has not been initialized");
        }
        return instance;
    }

    private final SessionFactory sessionFactory;

    private PlayerSkillRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(PlayerSkillEntity player) {
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

    public void updateById(int id, PlayerSkillEntity updatedPlayerSkill) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            PlayerSkillEntity player = session.get(PlayerSkillEntity.class, id);
            if (player != null) {;
                player.setUserId(updatedPlayerSkill.getUserId());
                session.update(player);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public PlayerSkillEntity getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(PlayerSkillEntity.class, id);
        }
    }

    @SuppressWarnings("unchecked")
    public List<PlayerSkillEntity> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from PlayerSkillEntity", PlayerSkillEntity.class).list();  // Query simplified
        }
    }

    public void deleteById(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            PlayerSkillEntity player = session.get(PlayerSkillEntity.class, id);
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
