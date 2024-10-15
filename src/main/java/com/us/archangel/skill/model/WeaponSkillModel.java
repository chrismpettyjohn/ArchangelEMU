package com.us.archangel.skill.model;

public class WeaponSkillModel extends SkillModel<String> {

    public WeaponSkillModel(int baseXP, int levelMultiplier, int linearIncrease) {
        super(baseXP, levelMultiplier, linearIncrease);
    }

    @Override
    public String getType() {
        return "WeaponModel";
    }
}