package com.us.archangel.sanction.repository;

import com.us.archangel.sanction.entity.SanctionEntity;
import com.us.nova.core.GenericRepository;


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


}
