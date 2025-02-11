package com.us.archangel.gang;

import com.us.archangel.gang.context.GangContext;
import com.us.archangel.gang.context.GangInviteContext;
import com.us.archangel.gang.context.GangRoleContext;
import com.us.archangel.gang.mapper.GangInviteMapper;
import com.us.archangel.gang.mapper.GangMapper;
import com.us.archangel.gang.mapper.GangRoleMapper;
import com.us.archangel.gang.model.GangInviteModel;
import com.us.archangel.gang.model.GangModel;
import com.us.archangel.gang.model.GangRoleModel;
import com.us.archangel.gang.repository.GangInviteRepository;
import com.us.archangel.gang.repository.GangRepository;
import com.us.archangel.gang.repository.GangRoleRepository;
import com.us.archangel.gang.service.GangInviteService;
import com.us.archangel.gang.service.GangRoleService;
import com.us.archangel.gang.service.GangService;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public class GangManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(GangManager.class);

    private static GangManager instance;

    public static GangManager getInstance() {
        if (instance == null) {
            instance = new GangManager();
        }
        return instance;
    }

    private final GangRepository gangRepository;
    private final GangContext gangContext;
    private final GangService gangService;

    private final GangRoleRepository gangRoleRepository;
    private final GangRoleContext gangRoleContext;
    private final GangRoleService gangRoleService;

    private final GangInviteRepository gangInviteRepository;
    private final GangInviteContext gangInviteContext;
    private final GangInviteService gangInviteService;

    private GangManager() {
        this.gangContext = GangContext.getInstance();
        this.gangRepository = GangRepository.getInstance();
        this.gangService = GangService.getInstance();

        this.gangRoleContext = GangRoleContext.getInstance();
        this.gangRoleRepository = GangRoleRepository.getInstance();
        this.gangRoleService = GangRoleService.getInstance();

        this.gangInviteContext = GangInviteContext.getInstance();
        this.gangInviteRepository = GangInviteRepository.getInstance();
        this.gangInviteService = GangInviteService.getInstance();
        this.load();
    }

    public void load() {
        LOGGER.info("Gang manager > starting");

        this.gangService.getAll();
        this.gangRoleService.getAll();
        this.gangInviteService.getAll();

        LOGGER.info("Gang manager > loaded");
    }

    public void dispose() {
        for (GangModel gangModel : this.gangContext.getAll().values()) {
            this.gangRepository.updateById(gangModel.getId(), GangMapper.toEntity(gangModel));
            this.gangContext.delete(gangModel.getId());
        }

        for (GangRoleModel gangRoleModel : this.gangRoleContext.getAll().values()) {
            this.gangRoleRepository.updateById(gangRoleModel.getId(), GangRoleMapper.toEntity(gangRoleModel));
            this.gangRoleContext.delete(gangRoleModel.getId());
        }

        for (GangInviteModel gangInviteModel : this.gangInviteContext.getAll().values()) {
            this.gangInviteRepository.updateById(gangInviteModel.getId(), GangInviteMapper.toEntity(gangInviteModel));
            this.gangInviteContext.delete(gangInviteModel.getId());
        }
        LOGGER.info("Gang manager > disposed");
    }
}
