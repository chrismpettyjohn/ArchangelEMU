package com.us.archangel.sanction.repository;

import com.us.archangel.sanction.entity.SanctionEntity;
import com.us.nova.core.GenericRepository;

import java.util.List;


public class SanctionRepository extends GenericRepository<SanctionEntity> {

    private static SanctionRepository instance;

    public static SanctionRepository getInstance() {
        if (instance == null) {
            instance = new SanctionRepository();
        }
        return instance;
    }

    private SanctionRepository() {
        super(SanctionEntity.class);
    }

    public SanctionEntity create(SanctionEntity entity) {
        return super.create(entity);
    }

    public void updateById(int id, SanctionEntity entity) {
        super.updateById(id, entity);
    }

    public SanctionEntity getById(int id) {
        return super.getById(id);
    }

    public List<SanctionEntity> getAll() {
        return super.getAll();
    }

    public void deleteById(int id) {
        super.deleteById(id);
    }

}
