package com.us.archangel.feature.weapon.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.weapon.packets.outgoing.PlayerWeaponListComposer;
import com.us.archangel.player.model.PlayerWeaponModel;
import com.us.archangel.player.model.PlayerWeaponPermissions;
import com.us.archangel.player.service.PlayerService;
import com.us.archangel.player.service.PlayerWeaponService;

import java.util.List;

public class PlayerWeaponQueryListEvent extends MessageHandler {
    @Override
    public void handle() {
        boolean canReadPLayerWeapons = this.client.getHabbo().hasPermissionRight(PlayerWeaponPermissions.READ);

        if (!canReadPLayerWeapons) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.generic.not_allowed"));
            return;
        }

        int userId = this.packet.readInt();

        List<PlayerWeaponModel> playerWeaponModels = PlayerWeaponService.getInstance().getByUserID(userId);
        this.client.sendResponse(new PlayerWeaponListComposer(playerWeaponModels));
    }
}
