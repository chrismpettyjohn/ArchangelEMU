package com.us.archangel.skill.model;

public class MiningSkillModel extends SkillModel<String> {

    public MiningSkillModel(int baseXP, int levelMultiplier, int linearIncrease) {
        super(baseXP, levelMultiplier, linearIncrease);
    }

    @Override
    public String getType() {
        return "Mining";
    }
}