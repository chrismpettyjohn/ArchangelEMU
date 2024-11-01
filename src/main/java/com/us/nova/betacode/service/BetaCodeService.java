package com.us.nova.betacode.service;

import com.us.nova.betacode.context.BetaCodeContext;
import com.us.nova.betacode.entity.BetaCodeEntity;
import com.us.nova.betacode.mapper.BetaCodeMapper;
import com.us.nova.betacode.model.BetaCodeModel;
import com.us.nova.betacode.repository.BetaCodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BetaCodeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BetaCodeService.class);

    private static BetaCodeService instance;

    public static BetaCodeService getInstance() {
        if (instance == null) {
            instance = new BetaCodeService();
        }
        return instance;
    }

    private BetaCodeService() {
        LOGGER.info("Beta Code Service > starting");
        this.getAll();
        LOGGER.info("Beta Code Service > loaded {} beta codes", this.getAll().size());
    }

    public void create(BetaCodeEntity betaCodeEntity) {
        BetaCodeContext.getInstance().add(betaCodeEntity.getId(), BetaCodeMapper.toModel(betaCodeEntity));
        BetaCodeRepository.getInstance().create(betaCodeEntity);
    }

    public void update(int id, BetaCodeEntity updatedBetaCode) {
        BetaCodeContext.getInstance().update(id, BetaCodeMapper.toModel(updatedBetaCode));
        BetaCodeRepository.getInstance().updateById(id, updatedBetaCode);
    }
    
    public List<BetaCodeModel> getAll() {
        Map<Integer, BetaCodeModel> models = BetaCodeContext.getInstance().getAll();
        if (!models.isEmpty()) {
            return new ArrayList<>(models.values());
        }

        List<BetaCodeEntity> entities = BetaCodeRepository.getInstance().getAll();
        List<BetaCodeModel> modelList = entities.stream()
                .map(BetaCodeMapper::toModel)
                .collect(Collectors.toList());

        modelList.forEach(model -> BetaCodeContext.getInstance().add(model.getId(), model));
        return modelList;
    }


    public void deleteById(int id) {
        BetaCodeContext.getInstance().delete(id);
        BetaCodeRepository.getInstance().deleteById(id);
    }
}
