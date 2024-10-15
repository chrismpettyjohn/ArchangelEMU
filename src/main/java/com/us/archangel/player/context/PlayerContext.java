package com.us.archangel.player.context;

import com.us.archangel.core.GenericContext;
import com.us.archangel.player.model.PlayerModel;

public class PlayerContext extends GenericContext<PlayerModel> {

    private static PlayerContext instance;

    private PlayerContext() {
        super();
    }

    public static PlayerContext getInstance() {
        if (instance == null) {
            instance = new PlayerContext();
        }
        return instance;
    }
}
