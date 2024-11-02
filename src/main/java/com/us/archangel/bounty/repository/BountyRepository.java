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
