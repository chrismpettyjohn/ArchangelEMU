package com.us.archangel.sanction;

import com.us.archangel.sanction.context.SanctionContext;
import com.us.archangel.sanction.entity.SanctionEntity;
import com.us.archangel.sanction.mapper.SanctionMapper;
import com.us.archangel.sanction.model.SanctionModel;
import com.us.archangel.sanction.repository.SanctionRepository;
import com.us.archangel.sanction.service.SanctionService;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Getter
public class SanctionManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(SanctionManager.class);

    private static SanctionManager instance;

    public static SanctionManager getInstance() {
        if (instance == null) {
            instance = new SanctionManager();
        }
        return instance;
    }
    private final SanctionRepository sanctionRepository;

    private final SanctionContext sanctionContext;

    private final SanctionService sanctionService;

    private SanctionManager() {
        this.sanctionContext = SanctionContext.getInstance();
        this.sanctionRepository = SanctionRepository.getInstance();
        this.sanctionService = SanctionService.getInstance();
        this.load();
    }

    public void load() {
        LOGGER.info("Sanction manager > starting");

        this.sanctionService.getAll();

        LOGGER.info("Sanction manager > loaded");
    }

    public void dispose() {
        for (SanctionModel sanctionModel : this.sanctionContext.getAll().values()) {
            this.sanctionRepository.updateById(sanctionModel.getId(), SanctionMapper.toEntity(sanctionModel));
            this.sanctionContext.delete(sanctionModel.getId());
        }
        LOGGER.info("Sanction manager > disposed");
    }
}
