package com.eu.archangel.corp;

import com.eu.archangel.corp.context.CorpContext;
import com.eu.archangel.corp.entity.CorpEntity;
import com.eu.archangel.corp.mapper.CorpMapper;
import com.eu.archangel.corp.model.CorpModel;
import com.eu.archangel.corp.repository.CorpRepository;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Getter
public class CorpManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(CorpManager.class);

    private static CorpManager instance;

    public static CorpManager getInstance(SessionFactory sessionFactory) {
        if (instance == null) {
            instance = new CorpManager(sessionFactory);
        }
        return instance;
    }

    public static CorpManager getInstance() {
        if (instance == null) {
            throw new RuntimeException("CorpManager has not been initialized");
        }
        return instance;
    }

    private final CorpRepository corpRepository;

    private final CorpContext corpContext;

    private CorpManager(SessionFactory sessionFactory) {
        this.corpContext = CorpContext.getInstance();
        this.corpRepository = CorpRepository.getInstance(sessionFactory);
        this.load();
    }

    public void load() {
        LOGGER.info("Corp manager > starting");

        List<CorpEntity> corpEntities = CorpRepository.getInstance().getAll();

        for (CorpEntity corpEntity : corpEntities) {
            this.corpContext.add(corpEntity.getId(), CorpMapper.toModel(corpEntity));
        }

        LOGGER.info("Corp manager > loaded " + corpEntities.size() + " corps");
    }

    public void dispose() {
        for (CorpModel corpModel : this.corpContext.getAll().values()) {
            this.corpRepository.updateById(corpModel.getId(), CorpMapper.toEntity(corpModel));
            this.corpContext.delete(corpModel.getId());
        }
        LOGGER.info("Corp manager > disposed");
    }
}
