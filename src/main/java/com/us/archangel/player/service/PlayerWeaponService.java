package com.us.archangel.player.service;

import com.us.archangel.core.GenericContext;
import com.us.archangel.player.entity.PlayerWeaponEntity;
import com.us.archangel.player.mapper.PlayerWeaponMapper;
import com.us.archangel.player.model.PlayerWeaponModel;
import com.us.archangel.player.repository.PlayerWeaponRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PlayerWeaponService {

    private static com.us.archangel.player.service.PlayerWeaponService instance;

    private final GenericContext<PlayerWeaponModel> playerContext = new GenericContext<>();

    public static com.us.archangel.player.service.PlayerWeaponService getInstance() {
        if (instance == null) {
            instance = new com.us.archangel.player.service.PlayerWeaponService();
        }
        return instance;
    }

    public void create(PlayerWeaponEntity playerEntity) {
        playerContext.add(playerEntity.getId(), PlayerWeaponMapper.toModel(playerEntity));
        PlayerWeaponRepository.getInstance().create(playerEntity);
    }

    public void update(int id, PlayerWeaponEntity updatedPlayerWeapon) {
        playerContext.update(id, PlayerWeaponMapper.toModel(updatedPlayerWeapon));
        PlayerWeaponRepository.getInstance().updateById(id, updatedPlayerWeapon);
    }

    public List<PlayerWeaponModel> getByUserID(int userID) {
        // Check if there are any weapons in the context with the specified userID
        Map<Integer, PlayerWeaponModel> allWeapons = playerContext.getAll();
        List<PlayerWeaponModel> models = allWeapons.values().stream()
                .filter(weapon -> weapon.getPlayerId() == userID)
                .collect(Collectors.toList());

        if (!models.isEmpty()) {
            return models;
        }

        // Fallback to repository if no weapons found in context for the user
        List<PlayerWeaponEntity> entities = PlayerWeaponRepository.getInstance().getByPlayerId(userID);
        List<PlayerWeaponModel> modelList = entities.stream()
                .map(PlayerWeaponMapper::toModel)
                .collect(Collectors.toList());

        // Add to context for caching
        modelList.forEach(model -> playerContext.add(model.getId(), model));
        return modelList;
    }


    public List<PlayerWeaponModel> getAll() {
        // Fetch all models from context
        Map<Integer, PlayerWeaponModel> models = playerContext.getAll();
        if (!models.isEmpty()) {
            return new ArrayList<>(models.values());
        }

        // Fallback to repository if context is empty
        List<PlayerWeaponEntity> entities = PlayerWeaponRepository.getInstance().getAll();
        List<PlayerWeaponModel> modelList = entities.stream()
                .map(PlayerWeaponMapper::toModel)
                .collect(Collectors.toList());

        // Add to context
        modelList.forEach(model -> playerContext.add(model.getId(), model));
        return modelList;
    }

    public void deleteById(int id) {
        // Remove from context first
        playerContext.delete(id);
        // Then delete from repository as secondary
        PlayerWeaponRepository.getInstance().deleteById(id);
    }
}
