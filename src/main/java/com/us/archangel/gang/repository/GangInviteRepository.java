package com.us.archangel.gang.repository;

import com.us.archangel.gang.entity.GangInviteEntity;
import com.us.nova.core.GenericRepository;
import org.hibernate.Session;

import java.util.List;

public class GangInviteRepository extends GenericRepository<GangInviteEntity> {

    private static GangInviteRepository instance;

    public static synchronized GangInviteRepository getInstance() {
        if (instance == null) {
            instance = new GangInviteRepository();
        }
        return instance;
    }

    private GangInviteRepository() {
        super(GangInviteEntity.class);
    }

    public GangInviteEntity create(GangInviteEntity entity) {
       return super.create(entity);
    }

    public void updateById(int id, GangInviteEntity entity) {
        super.updateById(id, entity);
    }

    public GangInviteEntity getById(int id) {
        return super.getById(id);
    }

    public List<GangInviteEntity> getAll() {
        return super.getAll();
    }

    public GangInviteEntity getByGangAndUserId(int gangId, int userId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "from GangInviteEntity where gangId = :gangId AND userId = :userId", GangInviteEntity.class)
                    .setParameter("gangId", gangId)
                    .setParameter("userId", userId)
                    .getSingleResult();
        }
    }
}
