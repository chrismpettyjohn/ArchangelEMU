package com.eu.archangel.weapon.context;

import com.eu.archangel.core.GenericContext;
import com.eu.archangel.weapon.model.WeaponModel;

public class WeaponContext extends GenericContext<WeaponModel> {

    private static WeaponContext instance;

    private WeaponContext() {
        super();
    }

    public static WeaponContext getInstance() {
        if (instance == null) {
            instance = new WeaponContext();
        }
        return instance;
    }
}
