package com.us.archangel.bounty.repository;

import com.us.archangel.bounty.entity.BountyEntity;
import com.us.nova.core.GenericRepository;
import org.hibernate.Session;

import java.util.List;

public class BountyRepository extends GenericRepository<BountyEntity> {

    private static BountyRepository instance;

    public static synchronized BountyRepository getInstance() {
        if (instance == null) {
            instance = new BountyRepository();
        }
        return instance;
    }

    private BountyRepository() {
        super(BountyEntity.class);
    }

    public BountyEntity create(BountyEntity entity) {
        return super.create(entity);
    }

    public void updateById(int id, BountyEntity entity) {
        super.updateById(id, entity);
    }

    public BountyEntity getById(int id) {
        return super.getById(id);
    }

    public List<BountyEntity> getAll() {
        return super.getAll();
    }

    public List<BountyEntity> getByUserId(int userId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM BountyEntity WHERE userId = :userId", BountyEntity.class)
                    .setParameter("userId", userId)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
