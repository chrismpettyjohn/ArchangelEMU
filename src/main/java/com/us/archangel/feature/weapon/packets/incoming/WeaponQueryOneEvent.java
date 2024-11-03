package com.us.archangel.feature.weapon.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.weapon.packets.outgoing.WeaponDataComposer;
import com.us.archangel.weapon.model.WeaponModel;
import com.us.archangel.weapon.model.WeaponPermissions;
import com.us.archangel.weapon.service.WeaponService;

public class WeaponQueryOneEvent extends MessageHandler {

    @Override
    public void handle() {

        boolean canReadWeapons = this.client.getHabbo().hasPermissionRight(WeaponPermissions.READ);

        if (!canReadWeapons) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.generic.not_allowed"));
            return;
        }

        int weaponId = this.packet.readInt();
        WeaponModel weapon = WeaponService.getInstance().getById(weaponId);

        this.client.sendResponse(new WeaponDataComposer(weapon));
    }
}
