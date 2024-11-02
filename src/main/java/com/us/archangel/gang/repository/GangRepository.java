package com.us.archangel.gang.repository;

import com.us.archangel.gang.entity.GangEntity;
import com.us.nova.core.GenericRepository;
import org.hibernate.Session;

import java.util.List;

public class GangRepository extends GenericRepository<GangEntity> {

    private static GangRepository instance;

    public static synchronized GangRepository getInstance() {
        if (instance == null) {
            instance = new GangRepository();
        }
        return instance;
    }

    private GangRepository() {
        super(GangEntity.class);
    }

    public List<GangEntity> findManyByDisplayName(String displayName) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from GangEntity where displayName ILIKE :displayName", GangEntity.class)
                    .setParameter("displayName", "%" + displayName + "%")
                    .list();
        }
    }
}
