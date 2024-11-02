package com.us.archangel.corp.repository;

import com.us.archangel.corp.entity.CorpInviteEntity;
import com.us.nova.core.GenericRepository;
import org.hibernate.Session;

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

    public CorpInviteEntity getByCorpAndUserId(int corpId, int userId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from CorpInviteEntity where corpId = :corpId AND userId = :userId", CorpInviteEntity.class)
                    .setParameter("corpId", corpId)
                    .setParameter("userId", userId)
                    .getSingleResult();
        }
    }
}
