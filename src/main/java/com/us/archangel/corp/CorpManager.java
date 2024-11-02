package com.us.archangel.corp;

import com.us.archangel.corp.context.CorpContext;
import com.us.archangel.corp.context.CorpInviteContext;
import com.us.archangel.corp.context.CorpRoleContext;
import com.us.archangel.corp.entity.CorpEntity;
import com.us.archangel.corp.mapper.CorpInviteMapper;
import com.us.archangel.corp.mapper.CorpMapper;
import com.us.archangel.corp.mapper.CorpRoleMapper;
import com.us.archangel.corp.model.CorpInviteModel;
import com.us.archangel.corp.model.CorpModel;
import com.us.archangel.corp.model.CorpRoleModel;
import com.us.archangel.corp.repository.CorpInviteRepository;
import com.us.archangel.corp.repository.CorpRepository;
import com.us.archangel.corp.repository.CorpRoleRepository;
import com.us.archangel.corp.service.CorpInviteService;
import com.us.archangel.corp.service.CorpRoleService;
import com.us.archangel.corp.service.CorpService;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Getter
public class CorpManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(CorpManager.class);

    private static CorpManager instance;

    public static CorpManager getInstance() {
        if (instance == null) {
            instance = new CorpManager();
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

    private CorpManager() {
        this.corpContext = CorpContext.getInstance();
        this.corpRepository = CorpRepository.getInstance();
        this.corpService = CorpService.getInstance();

        this.corpRoleContext = CorpRoleContext.getInstance();
        this.corpRoleRepository = CorpRoleRepository.getInstance();
        this.corpRoleService = CorpRoleService.getInstance();

        this.corpInviteRepository = CorpInviteRepository.getInstance();
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

        LOGGER.info("Corp manager > loaded");
    }

    public void dispose() {
        for (CorpModel corpModel : this.corpContext.getAll().values()) {
            this.corpRepository.updateById(corpModel.getId(), CorpMapper.toEntity(corpModel));
            this.corpContext.delete(corpModel.getId());
        }

        for (CorpRoleModel corpRoleModel : this.corpRoleContext.getAll().values()) {
            this.corpRoleRepository.updateById(corpRoleModel.getId(), CorpRoleMapper.toEntity(corpRoleModel));
            this.corpRoleContext.delete(corpRoleModel.getId());
        }

        for (CorpInviteModel corpInviteModel : this.corpInviteContext.getAll().values()) {
            this.corpInviteRepository.updateById(corpInviteModel.getId(), CorpInviteMapper.toEntity(corpInviteModel));
            this.corpInviteContext.delete(corpInviteModel.getId());
        }

        LOGGER.info("Corp manager > disposed");
    }
}
