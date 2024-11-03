package com.us.archangel.player.context;

import com.us.nova.core.GenericContext;
import com.us.archangel.player.model.PlayerBankAccountModel;

public class PlayerBankAccountContext extends GenericContext<PlayerBankAccountModel> {

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
        super(PlayerBankAccountModel.class);
    }
}
