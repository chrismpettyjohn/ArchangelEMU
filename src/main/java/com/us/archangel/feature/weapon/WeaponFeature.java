package com.us.archangel.feature.weapon;

import com.us.archangel.feature.IFeatureLifecycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeaponFeature implements IFeatureLifecycle {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeaponFeature.class);

    private static WeaponFeature instance;

    public static WeaponFeature getInstance() {
        if (instance == null) {
            instance = new WeaponFeature();
        }
        return instance;
    }

    @Override
    public void onInit() {
        LOGGER.info("WeaponFeature initialized.");
    }

    @Override
    public void onDispose() {
        LOGGER.info("WeaponFeature disposed.");
    }

}
