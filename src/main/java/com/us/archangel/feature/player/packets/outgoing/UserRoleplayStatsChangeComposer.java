package com.us.archangel.feature.player.packets.outgoing;

import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.archangel.player.enums.PlayerAction;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserRoleplayStatsChangeComposer extends MessageComposer {
    private final Habbo habbo;

    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.userRoleplayStatsChangeComposer);
        this.response.appendInt(this.habbo.getHabboInfo().getId());
        this.response.appendString(this.habbo.getHabboInfo().getUsername());
        this.response.appendString(this.habbo.getHabboInfo().getLook());
        this.response.appendInt(this.habbo.getHabboInfo().getCredits());
        this.response.appendInt(0); // TODO: Bank
        this.response.appendBoolean(this.habbo.getPlayer().isDead());
        this.response.appendBoolean(this.habbo.getPlayer().getCurrentAction() == PlayerAction.Stunned);
        this.response.appendBoolean(this.habbo.getPlayer().getCurrentAction() == PlayerAction.Cuffed);
        this.response.appendBoolean(this.habbo.getPlayer().isWorking());
        this.response.appendInt(0);
        this.response.appendInt(this.habbo.getPlayer().getHealthNow());
        this.response.appendInt(this.habbo.getPlayer().getHealthMax());
        this.response.appendInt(this.habbo.getPlayer().getEnergyNow());
        this.response.appendInt(this.habbo.getPlayer().getEnergyMax());
        this.response.appendInt(this.habbo.getPlayer().getHungerNow());
        this.response.appendInt(this.habbo.getPlayer().getHungerMax());
        this.response.appendInt(this.habbo.getInventory().getWeaponsComponent().getEquippedWeapon() != null ? this.habbo.getInventory().getWeaponsComponent().getEquippedWeapon().getWeaponId() : -1);
        this.response.appendInt(this.habbo.getInventory().getWeaponsComponent().getEquippedWeapon() != null ? this.habbo.getInventory().getWeaponsComponent().getEquippedWeapon().getAmmoRemaining() : 0);
        this.response.appendInt(this.habbo.getPlayer().getCorpId());
        this.response.appendInt(this.habbo.getPlayer().getCorpRoleId());
        if (this.habbo.getPlayer().getGang() != null) {
            this.response.appendInt(this.habbo.getPlayer().getGang().getId());
        } else {
            this.response.appendInt(0);
        }
        this.response.appendInt(0);
        return this.response;
    }
}
