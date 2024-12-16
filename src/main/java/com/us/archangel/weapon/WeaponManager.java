package com.us.archangel.weapon;

import com.us.archangel.weapon.context.WeaponContext;
import com.us.archangel.weapon.entity.WeaponEntity;
import com.us.archangel.weapon.mapper.WeaponMapper;
import com.us.archangel.weapon.model.WeaponModel;
import com.us.archangel.weapon.repository.WeaponRepository;
import com.us.archangel.weapon.service.WeaponService;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Getter
public class WeaponManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeaponManager.class);

    private static WeaponManager instance;

    public static WeaponManager getInstance() {
        if (instance == null) {
            instance = new WeaponManager();
        }
        return instance;
    }

    private final WeaponRepository weaponRepository;
    private final WeaponContext weaponContext;
    private final WeaponService weaponService;

    private WeaponManager() {
        this.weaponContext = WeaponContext.getInstance();
        this.weaponRepository = WeaponRepository.getInstance();
        this.weaponService = WeaponService.getInstance();
        this.load();
    }

    public void load() {
        LOGGER.info("Weapon manager > starting");

        this.weaponService.getAll();

        LOGGER.info("Weapon manager > loaded");
    }

    public void dispose() {
        for (WeaponModel weaponModel : this.weaponContext.getAll().values()) {
            this.weaponRepository.updateById(weaponModel.getId(), WeaponMapper.toEntity(weaponModel));
            this.weaponContext.delete(weaponModel.getId());
        }
        LOGGER.info("Weapon manager > disposed");
    }
}
