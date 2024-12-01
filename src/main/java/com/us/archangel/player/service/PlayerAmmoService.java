package com.us.archangel.player.service;

import com.us.archangel.player.context.PlayerAmmoContext;
import com.us.archangel.player.entity.PlayerAmmoEntity;
import com.us.archangel.player.mapper.PlayerAmmoMapper;
import com.us.archangel.player.model.PlayerAmmoModel;
import com.us.archangel.player.repository.PlayerAmmoRepository;
import com.us.nova.core.GenericService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PlayerAmmoService extends GenericService<PlayerAmmoModel, PlayerAmmoContext, PlayerAmmoRepository> {

    private static PlayerAmmoService instance;

    public static synchronized PlayerAmmoService getInstance() {
        if (instance == null) {
            instance = new PlayerAmmoService();
            instance.getAll(); // preload cache
        }
        return instance;
    }

    private PlayerAmmoService() {
        super(PlayerAmmoContext.getInstance(), PlayerAmmoRepository.getInstance(), PlayerAmmoMapper.class, PlayerAmmoEntity.class);
    }

    public PlayerAmmoModel create(PlayerAmmoModel model) {
        return super.create(model);
    }

    public void update(int id, PlayerAmmoModel model) {
        super.update(id, model);
    }

    public List<PlayerAmmoModel> getAll() {
        return super.getAll();
    }

    public PlayerAmmoModel getById(int id) {
        return super.getById(id);
    }

    public void deleteById(int id) {
        super.deleteById(id);
    }

    public List<PlayerAmmoModel> getByUserID(int userID) {
        Map<Integer, PlayerAmmoModel> allAmmos = context.getAll();
        List<PlayerAmmoModel> models = allAmmos.values().stream()
                .filter(ammo -> ammo.getUserId() == userID)
                .collect(Collectors.toList());

        if (!models.isEmpty()) {
            return models;
        }

        List<PlayerAmmoEntity> entities = repository.getByPlayerId(userID);
        List<PlayerAmmoModel> modelList = entities.stream()
                .map(PlayerAmmoMapper::toModel)
                .collect(Collectors.toList());

        modelList.forEach(model -> context.add(model.getId(), model));
        return modelList;
    }
}
