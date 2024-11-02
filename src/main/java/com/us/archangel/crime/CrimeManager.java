package com.us.archangel.crime;

import com.us.archangel.crime.context.CrimeContext;
import com.us.archangel.crime.entity.CrimeEntity;
import com.us.archangel.crime.mapper.CrimeMapper;
import com.us.archangel.crime.model.CrimeModel;
import com.us.archangel.crime.repository.CrimeRepository;
import com.us.archangel.crime.service.CrimeService;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Getter
public class CrimeManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(CrimeManager.class);

    private static CrimeManager instance;

    public static CrimeManager getInstance() {
        if (instance == null) {
            instance = new CrimeManager();
        }
        return instance;
    }

    private final CrimeRepository crimeRepository;
    private final CrimeContext crimeContext;
    private final CrimeService crimeService;

    private CrimeManager() {
        this.crimeContext = CrimeContext.getInstance();
        this.crimeRepository = CrimeRepository.getInstance();
        this.crimeService = CrimeService.getInstance();
        this.load();
    }

    public void load() {
        LOGGER.info("Crime manager > starting");

        List<CrimeEntity> entities = CrimeRepository.getInstance().getAll();

        for (CrimeEntity entity : entities) {
            this.crimeContext.add(entity.getId(), CrimeMapper.toModel(entity));
        }

        LOGGER.info("Crime manager > loaded");
    }

    public void dispose() {
        for (CrimeModel crimeModel : this.crimeContext.getAll().values()) {
            this.crimeRepository.updateById(crimeModel.getId(), CrimeMapper.toEntity(crimeModel));
            this.crimeContext.delete(crimeModel.getId());
        }
        LOGGER.info("Crime manager > disposed");
    }
}
