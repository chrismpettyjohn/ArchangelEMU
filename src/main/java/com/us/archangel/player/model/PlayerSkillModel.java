package com.us.archangel.player.model;

import com.us.archangel.skill.model.*;
import com.us.archangel.weapon.enums.WeaponType;
import com.us.archangel.weapon.model.WeaponModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PlayerSkillModel {

    private int id;
    private int userId;
    private int strengthXp;
    private int lumberjackXp;
    private int meleeXp;
    private int weaponXp;
    private int farmingXp;
    private int miningXp;
    private int fishingXp;
    private int staminaXp;

    public PlayerSkillModel() {
    }

    public void addStrengthXp(int strengthXp) {
        this.strengthXp += strengthXp;
    }

    public StrengthSkillModel getStrength() {
        return new StrengthSkillModel(this.strengthXp, 1, 1);
    }

    public void addLumberjackXp(int lumberjackXp) {
        this.lumberjackXp += lumberjackXp;
    }

    public LumberjackSkillModel getLumberjack() {
        return new LumberjackSkillModel(this.lumberjackXp, 1, 1);
    }

    public void addMeleeXp(int meleeXp) {
        this.meleeXp += meleeXp;
    }

    public MeleeSkillModel getMelee() {
        return new MeleeSkillModel(this.meleeXp, 1, 1);
    }

    public void addWeaponXp(int weaponXp) {
        this.weaponXp += weaponXp;
    }

    public WeaponSkillModel getWeapon() {
        return new WeaponSkillModel(this.weaponXp, 1, 1);
    }

    public void addFarmingXp(int farmingXp) {
        this.farmingXp += farmingXp;
    }

    public FarmingSkillModel getFarming() {
        return new FarmingSkillModel(this.farmingXp, 1, 1);
    }

    public void addMiningXp(int miningXp) {
        this.miningXp += miningXp;
    }

    public MiningSkillModel getMining() {
        return new MiningSkillModel(this.miningXp, 1, 1);
    }

    public void addFishingXp(int fishingXp) {
        this.fishingXp += fishingXp;
    }

    public FishingSkillModel getFishing() {
        return new FishingSkillModel(this.fishingXp, 1, 1);
    }

    public void addStaminaXp(int staminaXp) {
        this.staminaXp += staminaXp;
    }

    public StaminaSkillModel getStamina() {
        return new StaminaSkillModel(this.staminaXp, 1, 1);
    }

    public int getDamageModifier(WeaponModel weapon) {
        if (weapon == null) {
            return Math.min(this.getMelee().getCurrentLevel(), 1);
        }
        return weapon.getType() == WeaponType.MELEE ? this.getMelee().getCurrentLevel() : this.getWeapon().getCurrentLevel();
    }


}
