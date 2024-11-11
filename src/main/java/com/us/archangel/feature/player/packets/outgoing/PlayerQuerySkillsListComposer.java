package com.us.archangel.feature.player.packets.outgoing;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.archangel.player.model.PlayerSkillModel;
import com.us.archangel.player.service.PlayerSkillService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlayerQuerySkillsListComposer extends MessageComposer {
    private final int userID;

    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.playerQuerySkillsListComposer);

        PlayerSkillModel playerSkills = PlayerSkillService.getInstance().getByUserID(this.userID);

        this.response.appendInt(playerSkills.getUserId());
        this.response.appendInt(playerSkills.getStrengthXp());
        this.response.appendInt(playerSkills.getStrength().getCurrentLevel());
        this.response.appendInt(playerSkills.getLumberjackXp());
        this.response.appendInt(playerSkills.getLumberjack().getCurrentLevel());
        this.response.appendInt(playerSkills.getMeleeXp());
        this.response.appendInt(playerSkills.getMelee().getCurrentLevel());
        this.response.appendInt(playerSkills.getWeaponXp());
        this.response.appendInt(playerSkills.getWeapon().getCurrentLevel());
        this.response.appendInt(playerSkills.getFarmingXp());
        this.response.appendInt(playerSkills.getFarming().getCurrentLevel());
        this.response.appendInt(playerSkills.getMiningXp());
        this.response.appendInt(playerSkills.getMining().getCurrentLevel());
        this.response.appendInt(playerSkills.getFishingXp());
        this.response.appendInt(playerSkills.getFishing().getCurrentLevel());
        this.response.appendInt(playerSkills.getStaminaXp());
        this.response.appendInt(playerSkills.getStamina().getCurrentLevel());

        return this.response;
    }
}