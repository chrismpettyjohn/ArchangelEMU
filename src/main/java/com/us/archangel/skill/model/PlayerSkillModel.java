package com.us.archangel.skill.model;

public class PlayerSkillModel extends SkillModel<String> {

    public PlayerSkillModel(int baseXP, int levelMultiplier, int linearIncrease) {
        super(baseXP, levelMultiplier, linearIncrease);
    }

    @Override
    public String getType() {
        return "Player";
    }
}