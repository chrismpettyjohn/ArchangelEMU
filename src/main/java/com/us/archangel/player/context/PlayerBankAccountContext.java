package com.us.archangel.player.context;

import com.us.archangel.core.GenericContext;
import com.us.archangel.player.model.PlayerSkillModel;

public class PlayerBankAccountContext extends GenericContext<PlayerSkillModel> {

    private static volatile PlayerBankAccountContext instance;

    public static PlayerBankAccountContext getInstance() {
        if (instance == null) {
            synchronized (PlayerBankAccountContext.class) {
                if (instance == null) {
                    instance = new PlayerBankAccountContext();
                }
            }
        }
        return instance;
    }

    protected PlayerBankAccountContext() {
        super();
    }
}
