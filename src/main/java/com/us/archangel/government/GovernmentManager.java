package com.us.archangel.government;

import com.us.archangel.government.context.GovernmentLawContext;
import com.us.archangel.government.mapper.GovernmentLawMapper;
import com.us.archangel.government.model.GovernmentLawModel;
import com.us.archangel.government.repository.GovernmentLawRepository;
import com.us.archangel.government.service.GovernmentLawService;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public class GovernmentManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(GovernmentManager.class);

    private static GovernmentManager instance;

    public static GovernmentManager getInstance() {
        if (instance == null) {
            instance = new GovernmentManager();
        }
        return instance;
    }

    private final GovernmentLawContext lawContext;
    private final GovernmentLawRepository lawRepository;
    private final GovernmentLawService lawService;

    private GovernmentManager() {
        LOGGER.info("Government manager > starting");

        this.lawContext = GovernmentLawContext.getInstance();
        this.lawRepository = GovernmentLawRepository.getInstance();
        this.lawService = GovernmentLawService.getInstance();

        LOGGER.info("Government manager > loaded");
        this.load();
    }

    public void load() {
        LOGGER.info("Government manager > starting");

        this.lawService.getAll();

        LOGGER.info("Government manager > loaded");
    }

    public void dispose() {
        for (GovernmentLawModel lawModel : this.lawContext.getAll().values()) {
            this.lawRepository.updateById(lawModel.getId(), GovernmentLawMapper.toEntity(lawModel));
            this.lawContext.delete(lawModel.getId());
        }

        LOGGER.info("Government manager > disposed");
    }
}
