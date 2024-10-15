package com.us.archangel.feature.police;

import com.us.archangel.feature.IFeatureLifecycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PoliceFeature implements IFeatureLifecycle {
    private static final Logger LOGGER = LoggerFactory.getLogger(PoliceFeature.class);

    private static PoliceFeature instance;

    public static PoliceFeature getInstance() {
        if (instance == null) {
            instance = new PoliceFeature();
        }
        return instance;
    }

    @Override
    public void onInit() {
        LOGGER.info("PoliceFeature initialized.");
    }

    @Override
    public void onDispose() {
        LOGGER.info("PoliceFeature disposed.");
    }

}
