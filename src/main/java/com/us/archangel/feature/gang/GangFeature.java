package com.us.archangel.feature.gang;

import com.us.archangel.feature.IFeatureLifecycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GangFeature implements IFeatureLifecycle {
    private static final Logger LOGGER = LoggerFactory.getLogger(GangFeature.class);

    private static GangFeature instance;

    public static GangFeature getInstance() {
        if (instance == null) {
            instance = new GangFeature();
        }
        return instance;
    }

    @Override
    public void onInit() {
        LOGGER.info("GangFeature initialized.");
    }

    @Override
    public void onDispose() {
        LOGGER.info("GangFeature disposed.");
    }

}
