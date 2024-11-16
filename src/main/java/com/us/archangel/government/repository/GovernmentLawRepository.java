package com.us.archangel.government.repository;

import com.us.archangel.government.entity.GovernmentLawEntity;
import com.us.nova.core.GenericRepository;

import java.util.List;

public class GovernmentLawRepository extends GenericRepository<GovernmentLawEntity> {

    private static GovernmentLawRepository instance;

    public static GovernmentLawRepository getInstance() {
        if (instance == null) {
            instance = new GovernmentLawRepository();
        }
        return instance;
    }
    private GovernmentLawRepository() {
        super(GovernmentLawEntity.class);
    }

    public GovernmentLawEntity create(GovernmentLawEntity entity) {
        return super.create(entity);
    }

    public void updateById(int id, GovernmentLawEntity entity) {
        super.updateById(id, entity);
    }

    public GovernmentLawEntity getById(int id) {
        return super.getById(id);
    }

    public List<GovernmentLawEntity> getAll() {
        return super.getAll();
    }
}
