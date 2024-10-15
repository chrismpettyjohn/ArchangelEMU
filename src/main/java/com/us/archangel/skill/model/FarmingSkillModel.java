package com.us.archangel.skill.model;

public class FarmingSkillModel extends SkillModel<String> {

    public FarmingSkillModel(int baseXP, int levelMultiplier, int linearIncrease) {
        super(baseXP, levelMultiplier, linearIncrease);
    }

    @Override
    public String getType() {
        return "Farming";
    }
}