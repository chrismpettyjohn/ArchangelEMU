package com.us.archangel.skill.model;

public class AccuracySkillModel extends SkillModel<String> {

    public AccuracySkillModel(int baseXP, int levelMultiplier, int linearIncrease) {
        super(baseXP, levelMultiplier, linearIncrease);
    }

    @Override
    public String getType() {
        return "Accuracy";
    }
}