package com.us.archangel.player.context;

import com.us.nova.core.GenericContext;
import com.us.archangel.player.model.PlayerSkillModel;

public class PlayerSkillContext extends GenericContext<PlayerSkillModel> {

    private static volatile PlayerSkillContext instance;

    public static PlayerSkillContext getInstance() {
        if (instance == null) {
            synchronized (PlayerSkillContext.class) {
                if (instance == null) {
                    instance = new PlayerSkillContext();
                }
            }
        }
        return instance;
    }

    protected PlayerSkillContext() {
        super(PlayerSkillModel.class);
    }
}
