package com.us.archangel.weapon.context;

import com.us.archangel.core.GenericContext;
import com.us.archangel.weapon.model.WeaponModel;

public class WeaponContext extends GenericContext<WeaponModel> {

    private static volatile WeaponContext instance;

    public static WeaponContext getInstance() {
        if (instance == null) {
            synchronized (WeaponContext.class) {
                if (instance == null) {
                    instance = new WeaponContext();
                }
            }
        }
        return instance;
    }

    protected WeaponContext() {
        super();
    }
}
