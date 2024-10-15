package com.us.archangel.weapon;

import com.us.archangel.weapon.context.WeaponContext;
import com.us.archangel.weapon.entity.WeaponEntity;
import com.us.archangel.weapon.mapper.WeaponMapper;
import com.us.archangel.weapon.model.WeaponModel;
import com.us.archangel.weapon.repository.WeaponRepository;
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

        List<WeaponEntity> entities = WeaponRepository.getInstance().getAll();

        for (WeaponEntity entity : entities) {
            this.weaponContext.add(entity.getId(), WeaponMapper.toModel(entity));
        }

        LOGGER.info("Weapon manager > loaded " + entities.size() + " weapons");
    }

    public void dispose() {
        for (WeaponModel weaponModel : this.weaponContext.getAll().values()) {
            this.weaponRepository.updateById(weaponModel.getId(), WeaponMapper.toEntity(weaponModel));
            this.weaponContext.delete(weaponModel.getId());
        }
        LOGGER.info("Weapon manager > disposed");
    }
}
