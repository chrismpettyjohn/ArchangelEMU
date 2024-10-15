package com.us.archangel.skill.model;

public class StaminaSkillModel extends SkillModel<String> {

    public StaminaSkillModel(int baseXP, int levelMultiplier, int linearIncrease) {
        super(baseXP, levelMultiplier, linearIncrease);
    }

    @Override
    public String getType() {
        return "Stamina";
    }
}