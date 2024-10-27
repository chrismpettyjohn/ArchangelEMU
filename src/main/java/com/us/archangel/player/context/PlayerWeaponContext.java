package com.us.archangel.player.context;

import com.us.archangel.core.GenericContext;

public class PlayerWeaponContext extends GenericContext<PlayerWeaponContext> {

    private static volatile PlayerWeaponContext instance;

    public static PlayerWeaponContext getInstance() {
        if (instance == null) {
            synchronized (PlayerWeaponContext.class) {
                if (instance == null) {
                    instance = new PlayerWeaponContext();
                }
            }
        }
        return instance;
    }

    protected PlayerWeaponContext() {
        super();
    }
}
