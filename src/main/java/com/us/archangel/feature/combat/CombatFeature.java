package com.us.archangel.feature.combat;

import com.us.archangel.feature.IFeatureLifecycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CombatFeature implements IFeatureLifecycle {
    private static final Logger LOGGER = LoggerFactory.getLogger(CombatFeature.class);

    private static CombatFeature instance;

    public static CombatFeature getInstance() {
        if (instance == null) {
            instance = new CombatFeature();
        }
        return instance;
    }

    @Override
    public void onInit() {
        LOGGER.info("CombatFeature initialized.");
    }

    @Override
    public void onDispose() {
        LOGGER.info("CombatFeature disposed.");
    }

}
