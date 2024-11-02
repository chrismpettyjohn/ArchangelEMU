package com.us.archangel.crime.service;

import com.us.archangel.crime.context.CrimeContext;
import com.us.archangel.crime.entity.CrimeEntity;
import com.us.archangel.crime.mapper.CrimeMapper;
import com.us.archangel.crime.model.CrimeModel;
import com.us.archangel.crime.repository.CrimeRepository;
import com.us.nova.core.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CrimeService extends GenericService<CrimeModel, CrimeContext, CrimeRepository> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CrimeService.class);

    private static CrimeService instance;

    public static synchronized CrimeService getInstance() {
        if (instance == null) {
            instance = new CrimeService();
        }
        return instance;
    }

    private CrimeService() {
        super(CrimeContext.getInstance(), CrimeRepository.getInstance(), CrimeMapper.class);
        LOGGER.info("Crime Service > starting");
        this.getAll(); // preload
        LOGGER.info("Crime Service > loaded {} crimes", this.getAll().size());
    }

    public void create(CrimeEntity crimeEntity, CrimeModel crimeModel) {
        context.add(crimeEntity.getId(), crimeModel);
        repository.create(crimeEntity);
    }

    public void update(int id, CrimeEntity updatedCrime) {
        CrimeModel updatedModel = CrimeMapper.toModel(updatedCrime);
        context.update(id, updatedModel);
        repository.updateById(id, updatedCrime);
    }

    @Override
    public List<CrimeModel> getAll() {
        Map<Integer, CrimeModel> models = context.getAll();
        if (!models.isEmpty()) {
            return new ArrayList<>(models.values());
        }

        List<CrimeEntity> entities = repository.getAll();
        List<CrimeModel> modelList = entities.stream()
                .map(CrimeMapper::toModel)
                .collect(Collectors.toList());

        modelList.forEach(model -> context.add(model.getId(), model));
        return modelList;
    }

    public void deleteById(int id) {
        context.delete(id);
        repository.deleteById(id);
    }
}
