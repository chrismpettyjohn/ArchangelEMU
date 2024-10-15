package com.us.archangel.player.context;

import com.us.archangel.core.GenericContext;
import com.us.archangel.player.model.PlayerSkillModel;

public class PlayerSkillContext extends GenericContext<PlayerSkillModel> {

    private static PlayerSkillContext instance;

    private PlayerSkillContext() {
        super();
    }

    public static PlayerSkillContext getInstance() {
        if (instance == null) {
            instance = new PlayerSkillContext();
        }
        return instance;
    }
}
