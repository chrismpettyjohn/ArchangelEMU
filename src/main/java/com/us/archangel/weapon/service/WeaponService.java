package com.us.archangel.weapon.service;

import com.us.archangel.core.GenericContext;
import com.us.archangel.weapon.entity.WeaponEntity;
import com.us.archangel.weapon.mapper.WeaponMapper;
import com.us.archangel.weapon.model.WeaponModel;
import com.us.archangel.weapon.repository.WeaponRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WeaponService {

    private static WeaponService instance;

    private final GenericContext<WeaponModel> weaponContext = new GenericContext<>();

    public static WeaponService getInstance() {
        if (instance == null) {
            instance = new WeaponService();
        }
        return instance;
    }

    public void create(WeaponEntity weaponEntity, WeaponModel weaponModel) {
        // Add to context first
        weaponContext.add(weaponEntity.getId(), weaponModel);
        // Persist to repository as secondary
        WeaponRepository.getInstance().create(weaponEntity);
    }

    public void update(int id, WeaponEntity updatedWeapon, WeaponModel updatedModel) {
        // Update context first
        weaponContext.update(id, updatedModel);
        // Update repository as secondary
        WeaponRepository.getInstance().updateById(id, updatedWeapon);
    }

    public WeaponModel getById(int id) {
        // Check context first
        WeaponModel storedVal = weaponContext.get(id);
        if (storedVal != null) {
            return storedVal;
        }

        // Fallback to repository if not in context
        WeaponEntity entity = WeaponRepository.getInstance().getById(id);
        if (entity == null) {
            return null; // Handle case when entity doesn't exist
        }

        // Map entity to model and add to context
        WeaponModel model = WeaponMapper.toModel(entity);
        weaponContext.add(entity.getId(), model);
        return model;
    }

    public List<WeaponModel> getAll() {
        // Fetch all models from context
        Map<Integer, WeaponModel> models = weaponContext.getAll();
        if (!models.isEmpty()) {
            return new ArrayList<>(models.values());
        }

        // Fallback to repository if context is empty
        List<WeaponEntity> entities = WeaponRepository.getInstance().getAll();
        List<WeaponModel> modelList = entities.stream()
                .map(WeaponMapper::toModel)
                .collect(Collectors.toList());

        // Add to context
        modelList.forEach(model -> weaponContext.add(model.getId(), model));
        return modelList;
    }


    public void deleteById(int id) {
        // Remove from context first
        weaponContext.delete(id);
        // Then delete from repository as secondary
        WeaponRepository.getInstance().deleteById(id);
    }
}
