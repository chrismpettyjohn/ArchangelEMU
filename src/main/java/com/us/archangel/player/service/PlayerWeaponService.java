package com.us.archangel.player.service;

import com.us.archangel.ammo.entity.AmmoEntity;
import com.us.archangel.ammo.model.AmmoModel;
import com.us.archangel.ammo.repository.AmmoRepository;
import com.us.archangel.player.context.PlayerWeaponContext;
import com.us.archangel.player.entity.PlayerWeaponEntity;
import com.us.archangel.player.mapper.PlayerWeaponMapper;
import com.us.archangel.player.model.PlayerWeaponModel;
import com.us.archangel.player.repository.PlayerWeaponRepository;
import com.us.archangel.weapon.model.WeaponModel;
import com.us.nova.core.GenericService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PlayerWeaponService extends GenericService<PlayerWeaponModel, PlayerWeaponContext, PlayerWeaponRepository> {

    private static PlayerWeaponService instance;

    public static synchronized PlayerWeaponService getInstance() {
        if (instance == null) {
            instance = new PlayerWeaponService();
            instance.getAll(); // preload cache
        }
        return instance;
    }

    private PlayerWeaponService() {
        super(PlayerWeaponContext.getInstance(), PlayerWeaponRepository.getInstance(), PlayerWeaponMapper.class, PlayerWeaponEntity.class);
    }

    public PlayerWeaponModel create(PlayerWeaponModel model) {
        return super.create(model);
    }

    public PlayerWeaponModel createWithAmmo(WeaponModel weaponModel, int userId, AmmoModel ammoModel) {
        // Add ammo first
        PlayerAmmoService.getInstance().addAmmo(userId, ammoModel.getId(), weaponModel.getAmmoCapacity() * 2);
        
        // Get the proper AmmoEntity
        AmmoEntity ammoEntity = AmmoRepository.getInstance().getById(ammoModel.getId());
        
        // Create weapon with the entity relationship
        PlayerWeaponEntity entity = new PlayerWeaponEntity();
        entity.setUserId(userId);
        entity.setWeaponId(weaponModel.getId());
        entity.setAmmo(ammoEntity);  // This will set both the entity and ID
        entity.setAmmoRemaining(weaponModel.getAmmoCapacity());
        
        PlayerWeaponEntity saved = repository.create(entity);
        return PlayerWeaponMapper.toModel(saved);
    }

    public void update(int id, PlayerWeaponModel model) {
        super.update(id, model);
    }

    public List<PlayerWeaponModel> getAll() {
        return super.getAll();
    }

    public PlayerWeaponModel getById(int id) {
        return super.getById(id);
    }

    public void deleteById(int id) {
        super.deleteById(id);
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