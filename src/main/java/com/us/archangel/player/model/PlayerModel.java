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
import com.us.nova.core.NotificationHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

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
    @Setter
    private int healthNow;
    @Setter
    private int healthMax;
    @Setter
    private int energyNow;
    @Setter
    private int energyMax;
    @Setter
    private int armorNow;
    @Setter
    private int armorMax;
    @Setter
    private int hungerNow;
    @Setter
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

    public static List<PlayerAction> blockedStates = List.of(PlayerAction.Cuffed, PlayerAction.Escorted, PlayerAction.Stunned, PlayerAction.HospitalHealing);

    public boolean canWalk() {
        if (this.isDead()) {
            return false;
        }

        if (PlayerModel.blockedStates.contains(this.getCurrentAction())) {
            return false;
        }

        return true;
    }

    public boolean canInteract() {
        if (this.isDead()) {
            return false;
        }

        if (PlayerModel.blockedStates.contains(this.getCurrentAction())) {
            return false;
        }
        return true;
    }

    public void save() {
        PlayerService.getInstance().update(this.id, this);
    }

}

