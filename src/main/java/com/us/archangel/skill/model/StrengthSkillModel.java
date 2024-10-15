package com.us.archangel.skill.model;

public class StrengthSkillModel extends SkillModel<String> {

    public StrengthSkillModel(int baseXP, int levelMultiplier, int linearIncrease) {
        super(baseXP, levelMultiplier, linearIncrease);
    }

    @Override
    public String getType() {
        return "Strength";
    }
}