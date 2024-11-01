package com.us.archangel.weapon.service;

import com.us.archangel.bounty.service.BountyService;
import com.us.archangel.weapon.context.WeaponContext;
import com.us.archangel.weapon.entity.WeaponEntity;
import com.us.archangel.weapon.mapper.WeaponMapper;
import com.us.archangel.weapon.model.WeaponModel;
import com.us.archangel.weapon.repository.WeaponRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WeaponService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeaponService.class);

    private static WeaponService instance;

    public static WeaponService getInstance() {
        if (instance == null) {
            instance = new WeaponService();
        }
        return instance;
    }

    public void create(WeaponEntity weaponEntity, WeaponModel weaponModel) {
        WeaponContext.getInstance().add(weaponEntity.getId(), weaponModel);
        WeaponRepository.getInstance().create(weaponEntity);
    }

    public void update(int id, WeaponEntity updatedWeapon, WeaponModel updatedModel) {
        WeaponContext.getInstance().update(id, updatedModel);
        WeaponRepository.getInstance().updateById(id, updatedWeapon);
    }

    public WeaponModel getById(int id) {
        WeaponModel storedVal = WeaponContext.getInstance().get(id);
        if (storedVal != null) {
            return storedVal;
        }

        WeaponEntity entity = WeaponRepository.getInstance().getById(id);
        if (entity == null) {
            return null;
        }

        WeaponModel model = WeaponMapper.toModel(entity);
        WeaponContext.getInstance().add(entity.getId(), model);
        return model;
    }

    public List<WeaponModel> getAll() {
        Map<Integer, WeaponModel> models = WeaponContext.getInstance().getAll();
        if (!models.isEmpty()) {
            return new ArrayList<>(models.values());
        }

        List<WeaponEntity> entities = WeaponRepository.getInstance().getAll();
        List<WeaponModel> modelList = entities.stream()
                .map(WeaponMapper::toModel)
                .collect(Collectors.toList());

        modelList.forEach(model -> WeaponContext.getInstance().add(model.getId(), model));
        return modelList;
    }


    public void deleteById(int id) {
        WeaponContext.getInstance().delete(id);
        WeaponRepository.getInstance().deleteById(id);
    }
}
