package com.us.archangel.player.model;

import com.us.archangel.corp.model.CorpModel;
import com.us.archangel.corp.model.CorpRoleModel;
import com.us.archangel.corp.service.CorpRoleService;
import com.us.archangel.corp.service.CorpService;
import com.us.archangel.gang.model.GangModel;
import com.us.archangel.gang.model.GangRoleModel;
import com.us.archangel.gang.service.GangRoleService;
import com.us.archangel.gang.service.GangService;
import com.us.archangel.player.enums.PlayerAction;
import com.us.archangel.player.service.PlayerService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class PlayerModel {

    private int id;
    private int userId;
    @Setter
    private Integer gangId;;
    @Setter
    private Integer gangRoleId;
    @Setter
    private int corpId;
    @Setter
    private int corpRoleId;
    private int healthNow;
    private int healthMax;
    private int energyNow;
    private int energyMax;
    private int armorNow;
    private int armorMax;
    private int hungerNow;
    private int hungerMax;
    private short lastPosX;
    private short lastPosY;
    @Setter
    private long workTimeRemainingSecs;
    @Setter
    private long combatDelayExpiresAt;
    @Setter
    private long jailTimeRemainingSecs;
    @Setter
    private PlayerAction currentAction;
    @Setter
    private Integer escortingPlayerId;

    public PlayerModel() {
    }

    public void addHealth(int health) {
        this.healthNow += Math.max(health, this.healthMax);
    }

    public void depleteHealth(int health) {
        this.healthNow -= Math.max(health, 0);
    }

    public void addEnergy(int energy) {
        this.energyNow += Math.max(energy, this.energyMax);
    }

    public void depleteEnergy(int energy) {
        this.energyNow -= Math.min(energy, 0);
    }

    public void addArmor(int armor) {
        this.armorNow += Math.max(armor, this.armorMax);
    }

    public void depleteArmor(int armor) {
        this.hungerNow -= Math.max(armor, 0);
    }

    public void addHunger(int hunger) {
        this.hungerNow += Math.max(hunger, this.armorMax);
    }

    public void depleteHunger(int hunger) {
        this.hungerNow -= Math.max(hunger, 0);
    }

    public boolean isWorking() {
        return this.workTimeRemainingSecs > 0;
    }

    public boolean isCombatBlocked() {
        return System.currentTimeMillis() < this.combatDelayExpiresAt;
    }

    public CorpModel getCorp() {
        return CorpService.getInstance().getById(this.corpId);
    }

    public CorpRoleModel getCorpRole() {
        return CorpRoleService .getInstance().getById(this.corpRoleId);
    }

    public GangModel getGang() {
        if (this.gangId == null) {
            return null;
        }
        return GangService.getInstance().getById(this.gangId);
    }

    public GangRoleModel getGangRole() {
        if (this.gangRoleId == null) {
            return null;
        }
        return GangRoleService.getInstance().getById(this.gangRoleId);
    }

    public void setLastPos(short x, short y) {
        this.lastPosX = x;
        this.lastPosY = y;
    }

    public boolean isDead() {
        return this.healthNow  <= 0;
    }

    public void save() {
        PlayerService.getInstance().update(this.id, this);
    }
}

