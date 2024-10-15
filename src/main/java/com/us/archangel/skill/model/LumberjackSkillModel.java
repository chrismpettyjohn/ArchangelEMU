package com.us.archangel.skill.model;

public class LumberjackSkillModel extends SkillModel<String> {

    public LumberjackSkillModel(int baseXP, int levelMultiplier, int linearIncrease) {
        super(baseXP, levelMultiplier, linearIncrease);
    }

    @Override
    public String getType() {
        return "Lumberjack";
    }
}