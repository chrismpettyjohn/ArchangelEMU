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

    public GangEntity create(GangEntity entity) {
        return super.create(entity);
    }

    public void updateById(int id, GangEntity entity) {
        super.updateById(id, entity);
    }

    public GangEntity getById(int id) {
        return super.getById(id);
    }

    public List<GangEntity> getAll() {
        return super.getAll();
    }

    public List<GangEntity> findManyByDisplayName(String displayName) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from GangEntity where displayName ILIKE :displayName", GangEntity.class)
                    .setParameter("displayName", "%" + displayName + "%")
                    .list();
        }
    }
}
