package com.us.archangel.ammo;

import com.us.archangel.ammo.context.AmmoContext;
import com.us.archangel.ammo.entity.AmmoEntity;
import com.us.archangel.ammo.mapper.AmmoMapper;
import com.us.archangel.ammo.model.AmmoModel;
import com.us.archangel.ammo.repository.AmmoRepository;
import com.us.archangel.ammo.service.AmmoService;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Getter
public class AmmoManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(AmmoManager.class);

    private static AmmoManager instance;

    public static AmmoManager getInstance() {
        if (instance == null) {
            instance = new AmmoManager();
        }
        return instance;
    }

    private final AmmoRepository ammoRepository;
    private final AmmoContext ammoContext;
    private final AmmoService ammoService;

    private AmmoManager() {
        this.ammoContext = AmmoContext.getInstance();
        this.ammoRepository = AmmoRepository.getInstance();
        this.ammoService = AmmoService.getInstance();
        this.load();
    }

    public void load() {
        LOGGER.info("Ammo manager > starting");

        this.ammoService.getAll();

        LOGGER.info("Ammo manager > loaded");
    }

    public void dispose() {
        for (AmmoModel ammoModel : this.ammoContext.getAll().values()) {
            this.ammoRepository.updateById(ammoModel.getId(), AmmoMapper.toEntity(ammoModel));
            this.ammoContext.delete(ammoModel.getId());
        }
        LOGGER.info("Ammo manager > disposed");
    }
}
