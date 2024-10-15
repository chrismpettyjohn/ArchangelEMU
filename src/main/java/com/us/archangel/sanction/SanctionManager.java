package com.us.archangel.sanction;

import com.us.archangel.sanction.context.SanctionContext;
import com.us.archangel.sanction.entity.SanctionEntity;
import com.us.archangel.sanction.mapper.SanctionMapper;
import com.us.archangel.sanction.model.SanctionModel;
import com.us.archangel.sanction.repository.SanctionRepository;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Getter
public class SanctionManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(SanctionManager.class);

    private static SanctionManager instance;

    public static SanctionManager getInstance(SessionFactory sessionFactory) {
        if (instance == null) {
            instance = new SanctionManager(sessionFactory);
        }
        return instance;
    }

    public static SanctionManager getInstance() {
        if (instance == null) {
            throw new RuntimeException("SanctionManager has not been initialized");
        }
        return instance;
    }

    private final SanctionRepository sanctionRepository;

    private final SanctionContext sanctionContext;

    private SanctionManager(SessionFactory sessionFactory) {
        this.sanctionContext = SanctionContext.getInstance();
        this.sanctionRepository = SanctionRepository.getInstance(sessionFactory);
        this.load();
    }

    public void load() {
        LOGGER.info("Sanction manager > starting");

        List<SanctionEntity> entities = SanctionRepository.getInstance().getAll();

        for (SanctionEntity entity : entities) {
            this.sanctionContext.add(entity.getId(), SanctionMapper.toModel(entity));
        }

        LOGGER.info("Sanction manager > loaded " + entities.size() + " sanctions");
    }

    public void dispose() {
        for (SanctionModel sanctionModel : this.sanctionContext.getAll().values()) {
            this.sanctionRepository.updateById(sanctionModel.getId(), SanctionMapper.toEntity(sanctionModel));
            this.sanctionContext.delete(sanctionModel.getId());
        }
        LOGGER.info("Sanction manager > disposed");
    }
}
