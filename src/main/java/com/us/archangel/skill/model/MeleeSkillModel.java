package com.us.archangel.skill.model;

public class MeleeSkillModel extends SkillModel<String> {

    public MeleeSkillModel(int baseXP, int levelMultiplier, int linearIncrease) {
        super(baseXP, levelMultiplier, linearIncrease);
    }

    @Override
    public String getType() {
        return "Melee";
    }
}