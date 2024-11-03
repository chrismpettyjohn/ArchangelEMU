package com.us.archangel.feature.weapon.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.weapon.packets.outgoing.PlayerWeaponListComposer;
import com.us.archangel.player.model.PlayerWeaponModel;
import com.us.archangel.player.model.PlayerWeaponPermissions;
import com.us.archangel.player.service.PlayerWeaponService;

import java.util.List;

public class PlayerWeaponDeleteEvent extends MessageHandler {
    @Override
    public void handle() {
        boolean canDeletePlayerWeapons = this.client.getHabbo().hasPermissionRight(PlayerWeaponPermissions.DELETE);

        if (!canDeletePlayerWeapons) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.generic.not_allowed"));
            return;
        }

        PlayerWeaponModel playerWeaponModel = PlayerWeaponService.getInstance().getById(this.packet.readInt());

        PlayerWeaponService.getInstance().deleteById(playerWeaponModel.getId());

        List<PlayerWeaponModel> playerWeaponModels = PlayerWeaponService.getInstance().getByUserID(playerWeaponModel.getUserId());
        this.client.sendResponse(new PlayerWeaponListComposer(playerWeaponModels));
    }
}
