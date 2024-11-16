package com.us.archangel.corp.repository;

import com.us.archangel.corp.entity.CorpInviteEntity;
import com.us.nova.core.GenericRepository;
import org.hibernate.Session;

import java.util.List;

public class CorpInviteRepository extends GenericRepository<CorpInviteEntity> {

    private static CorpInviteRepository instance;

    public static synchronized CorpInviteRepository getInstance() {
        if (instance == null) {
            instance = new CorpInviteRepository();
        }
        return instance;
    }

    private CorpInviteRepository() {
        super(CorpInviteEntity.class);
    }

    public CorpInviteEntity create(CorpInviteEntity entity) {
        return super.create(entity);
    }

    public void updateById(int id, CorpInviteEntity entity) {
        super.updateById(id, entity);
    }

    public CorpInviteEntity getById(int id) {
        return super.getById(id);
    }

    public List<CorpInviteEntity> getAll() {
        return super.getAll();
    }

    public CorpInviteEntity getByCorpAndUserId(int corpId, int userId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from CorpInviteEntity where corpId = :corpId AND userId = :userId", CorpInviteEntity.class)
                    .setParameter("corpId", corpId)
                    .setParameter("userId", userId)
                    .getSingleResult();
        }
    }
}
