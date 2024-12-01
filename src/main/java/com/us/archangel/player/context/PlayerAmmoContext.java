package com.us.archangel.player.context;

import com.us.archangel.player.model.PlayerAmmoModel;
import com.us.nova.core.GenericContext;

public class PlayerAmmoContext extends GenericContext<PlayerAmmoModel> {

    private static volatile PlayerAmmoContext instance;

    public static PlayerAmmoContext getInstance() {
        if (instance == null) {
            synchronized (PlayerAmmoContext.class) {
                if (instance == null) {
                    instance = new PlayerAmmoContext();
                }
            }
        }
        return instance;
    }

    protected PlayerAmmoContext() {
        super(PlayerAmmoModel.class);
    }
}
