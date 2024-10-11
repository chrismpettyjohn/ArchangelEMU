package com.eu.archangel.weapon;

import com.eu.archangel.weapon.context.WeaponContext;
import com.eu.archangel.weapon.entity.WeaponEntity;
import com.eu.archangel.weapon.mapper.WeaponMapper;
import com.eu.archangel.weapon.model.WeaponModel;
import com.eu.archangel.weapon.repository.WeaponRepository;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Getter
public class WeaponManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeaponManager.class);

    private static WeaponManager instance;

    public static WeaponManager getInstance(SessionFactory sessionFactory) {
        if (instance == null) {
            instance = new WeaponManager(sessionFactory);
        }
        return instance;
    }

    public static WeaponManager getInstance() {
        if (instance == null) {
            throw new RuntimeException("WeaponManager has not been initialized");
        }
        return instance;
    }

    private final WeaponRepository weaponRepository;

    private final WeaponContext weaponContext;

    private WeaponManager(SessionFactory sessionFactory) {
        this.weaponContext = WeaponContext.getInstance();
        this.weaponRepository = WeaponRepository.getInstance(sessionFactory);
        this.load();
    }

    public void load() {
        LOGGER.info("Weapon manager > starting");

        List<WeaponEntity> weaponEntities = WeaponRepository.getInstance().getAll();

        for (WeaponEntity weaponEntity : weaponEntities) {
            this.weaponContext.add(weaponEntity.getId(), WeaponMapper.toModel(weaponEntity));
        }

        LOGGER.info("Weapon manager > loaded " + weaponEntities.size() + " weapons");
    }

    public void dispose() {
        for (WeaponModel weaponModel : this.weaponContext.getAll().values()) {
            this.weaponRepository.updateById(weaponModel.getId(), WeaponMapper.toEntity(weaponModel));
            this.weaponContext.delete(weaponModel.getId());
        }
        LOGGER.info("Weapon manager > disposed");
    }
}
