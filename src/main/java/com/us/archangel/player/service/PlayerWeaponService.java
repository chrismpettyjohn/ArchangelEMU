package com.us.archangel.player.service;

import com.us.archangel.player.context.PlayerWeaponContext;
import com.us.archangel.player.entity.PlayerWeaponEntity;
import com.us.archangel.player.mapper.PlayerWeaponMapper;
import com.us.archangel.player.model.PlayerWeaponModel;
import com.us.archangel.player.repository.PlayerWeaponRepository;
import com.us.nova.core.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PlayerWeaponService extends GenericService<PlayerWeaponModel, PlayerWeaponContext, PlayerWeaponRepository> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerWeaponService.class);

    private static PlayerWeaponService instance;

    public static synchronized PlayerWeaponService getInstance() {
        if (instance == null) {
            instance = new PlayerWeaponService();
            instance.getAll(); // preload cache
        }
        return instance;
    }

    private PlayerWeaponService() {
        super(PlayerWeaponContext.getInstance(), PlayerWeaponRepository.getInstance(), PlayerWeaponMapper.class, PlayerWeaponService.class);
        LOGGER.info("Player Weapon Service > starting");
        LOGGER.info("Player Weapon Service > loaded {} player weapons", this.getAll().size());
    }

    public List<PlayerWeaponModel> getByUserID(int userID) {
        Map<Integer, PlayerWeaponModel> allWeapons = context.getAll();
        List<PlayerWeaponModel> models = allWeapons.values().stream()
                .filter(weapon -> weapon.getUserId() == userID)
                .collect(Collectors.toList());

        if (!models.isEmpty()) {
            return models;
        }

        List<PlayerWeaponEntity> entities = repository.getByPlayerId(userID);
        List<PlayerWeaponModel> modelList = entities.stream()
                .map(PlayerWeaponMapper::toModel)
                .collect(Collectors.toList());

        modelList.forEach(model -> context.add(model.getId(), model));
        return modelList;
    }
}
