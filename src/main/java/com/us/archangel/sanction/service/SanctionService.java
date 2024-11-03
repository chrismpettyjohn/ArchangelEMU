package com.us.archangel.sanction.service;

import com.us.archangel.sanction.SanctionManager;
import com.us.archangel.sanction.context.SanctionContext;
import com.us.archangel.sanction.entity.SanctionEntity;
import com.us.archangel.sanction.model.SanctionModel;
import com.us.archangel.sanction.repository.SanctionRepository;
import com.us.nova.core.GenericService;

public class SanctionService extends GenericService<SanctionModel, SanctionContext, SanctionRepository>  {
    private static SanctionService instance;

    public static SanctionService getInstance() {
        if (instance == null) {
            instance = new SanctionService();
        }
        return instance;
    }

    private SanctionService() {
        super(SanctionContext.getInstance(), SanctionRepository.getInstance(), SanctionManager.class, SanctionEntity.class);
    }

}
