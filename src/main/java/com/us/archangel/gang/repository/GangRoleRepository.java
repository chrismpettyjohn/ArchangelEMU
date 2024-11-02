package com.us.archangel.gang.repository;

import com.us.archangel.gang.entity.GangRoleEntity;
import com.us.nova.core.GenericRepository;
import org.hibernate.Session;

import java.util.List;

public class GangRoleRepository extends GenericRepository<GangRoleEntity> {

    private static GangRoleRepository instance;

    public static synchronized GangRoleRepository getInstance() {
        if (instance == null) {
            instance = new GangRoleRepository();
        }
        return instance;
    }

    private GangRoleRepository() {
        super(GangRoleEntity.class);
    }

    public GangRoleEntity getByGangIdAndOrderId(int gangId, int orderId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "from GangRoleEntity where gangId = :gangId and orderId = :orderId", GangRoleEntity.class)
                    .setParameter("gangId", gangId)
                    .setParameter("orderId", orderId)
                    .uniqueResult();
        }
    }

    public List<GangRoleEntity> getByGangId(int gangId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from GangRoleEntity where gangId = :gangId", GangRoleEntity.class)
                    .setParameter("gangId", gangId)
                    .list();
        }
    }
}
