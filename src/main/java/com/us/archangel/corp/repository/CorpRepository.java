package com.us.archangel.corp.repository;

import com.us.archangel.corp.entity.CorpEntity;
import com.us.archangel.corp.enums.CorpIndustry;
import com.us.nova.core.GenericRepository;
import org.hibernate.Session;

import java.util.List;

public class CorpRepository extends GenericRepository<CorpEntity> {

    private static CorpRepository instance;

    public static synchronized CorpRepository getInstance() {
        if (instance == null) {
            instance = new CorpRepository();
        }
        return instance;
    }

    private CorpRepository() {
        super(CorpEntity.class);
    }

    public CorpEntity create(CorpEntity entity) {
        return super.create(entity);
    }

    public void updateById(int id, CorpEntity entity) {
        super.updateById(id, entity);
    }

    public CorpEntity getById(int id) {
        return super.getById(id);
    }

    public List<CorpEntity> getAll() {
        return super.getAll();
    }

    public List<CorpEntity> findManyByDisplayName(String displayName) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from CorpEntity where displayName ILIKE :displayName", CorpEntity.class)
                    .setParameter("displayName", "%" + displayName + "%")
                    .list();
        }
    }

    public List<CorpEntity> findManyByIndustry(CorpIndustry industry) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from CorpEntity where industry = :industry", CorpEntity.class)
                    .setParameter("industry", industry)
                    .list();
        }
    }

}

