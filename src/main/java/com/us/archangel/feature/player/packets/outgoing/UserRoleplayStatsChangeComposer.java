package com.us.archangel.feature.player.packets.outgoing;

import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.archangel.corp.model.CorpModel;
import com.us.archangel.corp.model.CorpRoleModel;
import com.us.archangel.corp.service.CorpRoleService;
import com.us.archangel.corp.service.CorpService;
import com.us.archangel.gang.model.GangModel;
import com.us.archangel.gang.model.GangRoleModel;
import com.us.archangel.gang.service.GangRoleService;
import com.us.archangel.gang.service.GangService;
import com.us.archangel.player.enums.PlayerAction;
import com.us.archangel.player.model.PlayerAmmoModel;
import com.us.archangel.player.model.PlayerWeaponModel;
import com.us.archangel.player.service.PlayerAmmoService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserRoleplayStatsChangeComposer extends MessageComposer {
    private final Habbo habbo;

    @Override
    protected ServerMessage composeInternal() {
        CorpModel corp = CorpService.getInstance().getById(this.habbo.getPlayer().getCorpId());
        CorpRoleModel corpRole = CorpRoleService.getInstance().getById(this.habbo.getPlayer().getCorpRoleId());

        GangModel gang = this.habbo.getPlayer().getGangRoleId() != null ?  GangService.getInstance().getById(this.habbo.getPlayer().getGangId()) : null;
        GangRoleModel gangRole = this.habbo.getPlayer().getGangRoleId() != null ? GangRoleService.getInstance().getByGangIdAndOrderId(gang.getId(), corpRole.getId()) : null;

        PlayerWeaponModel playerWeapon = this.habbo.getInventory().getWeaponsComponent().getEquippedWeapon();

        PlayerAmmoModel playerAmmo = playerWeapon != null ? PlayerAmmoService.getInstance().getByUserAndAmmoId(this.habbo.getPlayer().getId(), playerWeapon.getAmmoId()) : null;

        this.response.init(Outgoing.userRoleplayStatsChangeComposer);
        this.response.appendInt(this.habbo.getHabboInfo().getId());
        this.response.appendString(this.habbo.getHabboInfo().getUsername());
        this.response.appendString(this.habbo.getHabboInfo().getLook());
        this.response.appendString(this.habbo.getHabboInfo().getMotto());
        this.response.appendInt(this.habbo.getHabboInfo().getAccountCreated());
        this.response.appendInt(this.habbo.getHabboInfo().getLastOnline());
        this.response.appendBoolean(this.habbo.getHabboInfo().isOnline());
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
        this.response.appendInt(playerWeapon != null ? playerWeapon.getWeaponId() : -1);
        this.response.appendString(playerWeapon != null ? playerWeapon.getWeapon().getDisplayName() : "");
        this.response.appendString(playerWeapon != null ? playerWeapon.getWeapon().getUniqueName() : "");
        this.response.appendInt(playerWeapon != null ? playerWeapon.getAmmoId() : 0);
        this.response.appendInt(playerWeapon != null ? playerWeapon.getAmmoRemaining() : 0);
        this.response.appendInt(playerAmmo != null ? playerAmmo.getAmmoRemaining() : 0);
        this.response.appendInt(this.habbo.getPlayer().getCorpId());
        this.response.appendString(corp.getDisplayName());
        this.response.appendString(corp.getIndustry().toString());
        this.response.appendInt(this.habbo.getPlayer().getCorpRoleId());
        this.response.appendString(corpRole.getDisplayName());
        this.response.appendInt(this.habbo.getPlayer().getGangId() != null ? this.habbo.getPlayer().getGangId() : -1);
        this.response.appendString(gang != null ? gang.getDisplayName() : "");
        this.response.appendInt(this.habbo.getPlayer().getGangRoleId() != null ? this.habbo.getPlayer().getGangId() : -1);
        this.response.appendString(gangRole != null ? gangRole.getName() : "");
        this.response.appendInt(0);
        return this.response;
    }
}
