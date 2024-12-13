package com.us.archangel.player.context;

import com.us.archangel.player.model.PlayerKillHistoryModel;
import com.us.nova.core.GenericContext;

public class PlayerKillHistoryContext extends GenericContext<PlayerKillHistoryModel> {

    private static volatile PlayerKillHistoryContext instance;

    public static PlayerKillHistoryContext getInstance() {
        if (instance == null) {
            synchronized (PlayerKillHistoryContext.class) {
                if (instance == null) {
                    instance = new PlayerKillHistoryContext();
                }
            }
        }
        return instance;
    }

    protected PlayerKillHistoryContext() {
        super(PlayerKillHistoryModel.class);
    }
}
