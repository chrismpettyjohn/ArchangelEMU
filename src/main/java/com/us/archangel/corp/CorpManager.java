package com.us.archangel.corp;

import com.us.archangel.corp.context.CorpContext;
import com.us.archangel.corp.context.CorpInviteContext;
import com.us.archangel.corp.context.CorpRoleContext;
import com.us.archangel.corp.entity.CorpEntity;
import com.us.archangel.corp.mapper.CorpMapper;
import com.us.archangel.corp.model.CorpModel;
import com.us.archangel.corp.repository.CorpInviteRepository;
import com.us.archangel.corp.repository.CorpRepository;
import com.us.archangel.corp.repository.CorpRoleRepository;
import com.us.archangel.corp.service.CorpInviteService;
import com.us.archangel.corp.service.CorpRoleService;
import com.us.archangel.corp.service.CorpService;
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
    private final CorpInviteRepository corpInviteRepository;

    private final CorpContext corpContext;
    private final CorpRoleContext corpRoleContext;
    private final CorpInviteContext corpInviteContext;

    private final CorpService corpService;
    private final CorpRoleService corpRoleService;
    private final CorpInviteService corpInviteService;

    private CorpManager(SessionFactory sessionFactory) {
        this.corpContext = CorpContext.getInstance();
        this.corpRepository = CorpRepository.getInstance(sessionFactory);
        this.corpService = CorpService.getInstance();

        this.corpRoleContext = CorpRoleContext.getInstance();
        this.corpRoleRepository = CorpRoleRepository.getInstance(sessionFactory);
        this.corpRoleService = CorpRoleService.getInstance();

        this.corpInviteRepository = CorpInviteRepository.getInstance(sessionFactory);
        this.corpInviteContext = CorpInviteContext.getInstance();
        this.corpInviteService = CorpInviteService.getInstance();

        this.load();
    }

    public void load() {
        LOGGER.info("Corp manager > starting");

        List<CorpEntity> entities = CorpRepository.getInstance().getAll();

        for (CorpEntity entity : entities) {
            this.corpContext.add(entity.getId(), CorpMapper.toModel(entity));
        }

        LOGGER.info("Corp manager > loaded " + entities.size() + " corps");
    }

    public void dispose() {
        for (CorpModel corpModel : this.corpContext.getAll().values()) {
            this.corpRepository.updateById(corpModel.getId(), CorpMapper.toEntity(corpModel));
            this.corpContext.delete(corpModel.getId());
        }
        LOGGER.info("Corp manager > disposed");
    }
}
