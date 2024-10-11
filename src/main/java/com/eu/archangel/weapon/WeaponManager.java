package com.eu.archangel.weapon;

import com.eu.archangel.weapon.context.WeaponContext;
import com.eu.archangel.weapon.repository.WeaponRepository;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        LOGGER.info("Weapon manager > running");
    }

    public void dispose() {

    }
}
