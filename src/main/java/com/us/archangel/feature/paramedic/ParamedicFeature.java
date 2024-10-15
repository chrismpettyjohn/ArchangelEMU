package com.us.archangel.feature.paramedic;

import com.us.archangel.feature.IFeatureLifecycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParamedicFeature implements IFeatureLifecycle {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParamedicFeature.class);

    private static ParamedicFeature instance;

    public static ParamedicFeature getInstance() {
        if (instance == null) {
            instance = new ParamedicFeature();
        }
        return instance;
    }

    @Override
    public void onInit() {
        LOGGER.info("ParamedicFeature initialized.");
    }

    @Override
    public void onDispose() {
        LOGGER.info("ParamedicFeature disposed.");
    }

}
