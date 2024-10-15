package com.us.archangel.weapon.context;

import com.us.archangel.core.GenericContext;
import com.us.archangel.weapon.model.WeaponModel;

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
