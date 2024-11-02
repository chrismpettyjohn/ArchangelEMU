package com.us.archangel.gang.service;

import com.us.archangel.gang.context.GangContext;
import com.us.archangel.gang.mapper.GangMapper;
import com.us.archangel.gang.model.GangModel;
import com.us.archangel.gang.repository.GangRepository;
import com.us.nova.core.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GangService extends GenericService<GangModel, GangContext, GangRepository> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GangService.class);

    private static GangService instance;

    public static synchronized GangService getInstance() {
        if (instance == null) {
            instance = new GangService();
        }
        return instance;
    }

    private GangService() {
        super(GangContext.getInstance(), GangRepository.getInstance(), GangMapper.class);
        LOGGER.info("Gang Service > starting");
        this.getAll();  // Preload all gang data
        LOGGER.info("Gang Service > loaded {} gangs", this.getAll().size());
    }
}
