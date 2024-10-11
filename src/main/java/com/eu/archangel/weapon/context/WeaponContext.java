package com.eu.archangel.weapon.context;

import com.eu.archangel.core.GenericContext;
import com.eu.habbo.roleplay.weapons.Weapon;

public class WeaponContext extends GenericContext<Weapon> {

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
