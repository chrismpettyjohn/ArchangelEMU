package com.us.archangel.corp;

import com.us.archangel.corp.context.CorpContext;
import com.us.archangel.corp.context.CorpRoleContext;
import com.us.archangel.corp.entity.CorpEntity;
import com.us.archangel.corp.mapper.CorpMapper;
import com.us.archangel.corp.model.CorpModel;
import com.us.archangel.corp.repository.CorpRepository;
import com.us.archangel.corp.repository.CorpRoleRepository;
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

    private final CorpRoleRepository corpRoleRepository;

    private final CorpContext corpContext;

    private final CorpRoleContext corpRoleContext;

    private CorpManager(SessionFactory sessionFactory) {
        this.corpContext = CorpContext.getInstance();
        this.corpRepository = CorpRepository.getInstance(sessionFactory);

        this.corpRoleContext = CorpRoleContext.getInstance();
        this.corpRoleRepository = CorpRoleRepository.getInstance(sessionFactory);
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
