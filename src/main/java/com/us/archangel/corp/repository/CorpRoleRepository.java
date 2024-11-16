package com.us.archangel.corp.repository;

import com.us.archangel.corp.entity.CorpRoleEntity;
import com.us.nova.core.GenericRepository;
import org.hibernate.Session;

import java.util.List;

public class CorpRoleRepository extends GenericRepository<CorpRoleEntity> {

    private static CorpRoleRepository instance;

    public static synchronized CorpRoleRepository getInstance() {
        if (instance == null) {
            instance = new CorpRoleRepository();
        }
        return instance;
    }

    private CorpRoleRepository() {
        super(CorpRoleEntity.class);
    }

    public CorpRoleEntity create(CorpRoleEntity entity) {
        return super.create(entity);
    }

    public void updateById(int id, CorpRoleEntity entity) {
        super.updateById(id, entity);
    }

    public CorpRoleEntity getById(int id) {
        return super.getById(id);
    }

    public List<CorpRoleEntity> getAll() {
        return super.getAll();
    }

    public CorpRoleEntity getByCorpAndOrderId(int corpId, int orderId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from CorpRoleEntity where corpId = :corpId AND orderId = :orderId", CorpRoleEntity.class)
                    .setParameter("corpId", corpId)
                    .setParameter("orderId", orderId)
                    .getSingleResult();
        }
    }

    public List<CorpRoleEntity> findManyByCorpId(int corpId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from CorpRoleEntity where corpId = :corpId", CorpRoleEntity.class)
                    .setParameter("corpId", corpId)
                    .list();
        }
    }

}

