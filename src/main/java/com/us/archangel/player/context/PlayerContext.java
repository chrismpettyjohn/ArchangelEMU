package com.us.archangel.player.context;

import com.us.nova.core.GenericContext;
import com.us.archangel.player.model.PlayerModel;

public class PlayerContext extends GenericContext<PlayerModel> {

    private static volatile PlayerContext instance;

    public static PlayerContext getInstance() {
        if (instance == null) {
            synchronized (PlayerContext.class) {
                if (instance == null) {
                    instance = new PlayerContext();
                }
            }
        }
        return instance;
    }

    protected PlayerContext() {
        super(PlayerModel.class);
    }
}
