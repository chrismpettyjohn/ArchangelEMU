package com.us.archangel.sanction.service;

import com.us.archangel.sanction.context.SanctionContext;
import com.us.archangel.sanction.entity.SanctionEntity;
import com.us.archangel.sanction.mapper.SanctionMapper;
import com.us.archangel.sanction.model.SanctionModel;
import com.us.archangel.sanction.repository.SanctionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SanctionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SanctionService.class);

    private static SanctionService instance;

    public static SanctionService getInstance() {
        if (instance == null) {
            instance = new SanctionService();
        }
        return instance;
    }

    private SanctionService() {
        LOGGER.info("Sanction Service > starting");
        this.getAll();
        LOGGER.info("Sanction Service > loaded {} sanctions", this.getAll().size());
    }

    public void create(SanctionEntity sanctionEntity, SanctionModel sanctionModel) {
        SanctionContext.getInstance().add(sanctionEntity.getId(), sanctionModel);
        SanctionRepository.getInstance().create(sanctionEntity);
    }

    public void update(int id, SanctionEntity updatedSanction) {
        SanctionContext.getInstance().update(id, SanctionMapper.toModel(updatedSanction));
        SanctionRepository.getInstance().updateById(id, updatedSanction);
    }

    public List<SanctionModel> getAll() {
        Map<Integer, SanctionModel> models = SanctionContext.getInstance().getAll();
        if (!models.isEmpty()) {
            return new ArrayList<>(models.values());
        }

        List<SanctionEntity> entities = SanctionRepository.getInstance().getAll();
        List<SanctionModel> modelList = entities.stream()
                .map(SanctionMapper::toModel)
                .collect(Collectors.toList());

        modelList.forEach(model -> SanctionContext.getInstance().add(model.getId(), model));
        return modelList;
    }


    public void deleteById(int id) {
        SanctionContext.getInstance().delete(id);
        SanctionRepository.getInstance().deleteById(id);
    }
}
