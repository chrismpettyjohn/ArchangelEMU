package com.us.archangel.skill.model;

public class FishingSkillModel extends SkillModel<String> {

    public FishingSkillModel(int baseXP, int levelMultiplier, int linearIncrease) {
        super(baseXP, levelMultiplier, linearIncrease);
    }

    @Override
    public String getType() {
        return "Fishing";
    }
}