package com.us.archangel.feature;

import com.us.archangel.feature.bank.BankFeature;
import com.us.archangel.feature.combat.CombatFeature;
import com.us.archangel.feature.gang.GangFeature;
import com.us.archangel.feature.paramedic.ParamedicFeature;
import com.us.archangel.feature.police.PoliceFeature;
import com.us.archangel.feature.weapon.WeaponFeature;

import java.util.ArrayList;
import java.util.List;

public class RoleplayFeatureManager {

    private static RoleplayFeatureManager instance;

    public static RoleplayFeatureManager getInstance() {
        if (instance == null) {
            throw new RuntimeException("RoleplayFeatureManager has not been initialized");
        }
        return instance;
    }

    private final List<IFeatureLifecycle> features = new ArrayList<>();

    public RoleplayFeatureManager() {
        features.add(new BankFeature());
        features.add(new CombatFeature());
        features.add(new GangFeature());
        features.add(new ParamedicFeature());
        features.add(new PoliceFeature());
        features.add(new WeaponFeature());
        this.initializeFeatures();
    }

    public void initializeFeatures() {
        for (IFeatureLifecycle feature : features) {
            feature.onInit();
        }
    }

    public void disposeFeatures() {
        for (IFeatureLifecycle feature : features) {
            feature.onDispose();
        }
    }
}
