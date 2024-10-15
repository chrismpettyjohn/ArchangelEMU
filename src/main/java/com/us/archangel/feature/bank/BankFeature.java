package com.us.archangel.feature.bank;

import com.us.archangel.feature.IFeatureLifecycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BankFeature implements IFeatureLifecycle {
    private static final Logger LOGGER = LoggerFactory.getLogger(BankFeature.class);

    private static BankFeature instance;

    public static BankFeature getInstance() {
        if (instance == null) {
            instance = new BankFeature();
        }
        return instance;
    }

    @Override
    public void onInit() {
        LOGGER.info("BankManager initialized.");
    }

    @Override
    public void onDispose() {
        LOGGER.info("BankManager disposed.");
    }

}
