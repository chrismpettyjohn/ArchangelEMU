package com.us.archangel.crime.service;

import com.us.archangel.crime.context.CrimeContext;
import com.us.archangel.crime.entity.CrimeEntity;
import com.us.archangel.crime.mapper.CrimeMapper;
import com.us.archangel.crime.model.CrimeModel;
import com.us.archangel.crime.repository.CrimeRepository;
import com.us.archangel.gang.service.GangService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CrimeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CrimeService.class);

    private static CrimeService instance;


    public static CrimeService getInstance() {
        if (instance == null) {
            instance = new CrimeService();
        }
        return instance;
    }

    private CrimeService() {
        LOGGER.info("Crime Service > starting");
        this.getAll();
        LOGGER.info("Crime Service > loaded {} crimes", this.getAll().size());
    }

    public void create(CrimeEntity crimeEntity, CrimeModel crimeModel) {
        CrimeContext.getInstance().add(crimeEntity.getId(), crimeModel);
        CrimeRepository.getInstance().create(crimeEntity);
    }

    public void update(int id, CrimeEntity updatedCrime) {
        CrimeContext.getInstance().update(id, CrimeMapper.toModel(updatedCrime));
        CrimeRepository.getInstance().updateById(id, updatedCrime);
    }


    public List<CrimeModel> getAll() {
        Map<Integer, CrimeModel> models = CrimeContext.getInstance().getAll();
        if (!models.isEmpty()) {
            return new ArrayList<>(models.values());
        }

        List<CrimeEntity> entities = CrimeRepository.getInstance().getAll();
        List<CrimeModel> modelList = entities.stream()
                .map(CrimeMapper::toModel)
                .collect(Collectors.toList());

        modelList.forEach(model -> CrimeContext.getInstance().add(model.getId(), model));
        return modelList;
    }


    public void deleteById(int id) {
        CrimeContext.getInstance().delete(id);
        CrimeRepository.getInstance().deleteById(id);
    }
}
