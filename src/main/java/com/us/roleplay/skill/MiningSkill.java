package com.us.roleplay.skill;

public class MiningSkill extends Skill<String> {

    public MiningSkill(int baseXP, int levelMultiplier, int linearIncrease) {
        super(baseXP, levelMultiplier, linearIncrease);
    }

    @Override
    public String getType() {
        return "Mining";
    }
}