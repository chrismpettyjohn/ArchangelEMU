package com.us.archangel.feature.weapon.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.weapon.packets.outgoing.WeaponListComposer;
import com.us.archangel.weapon.model.WeaponModel;
import com.us.archangel.weapon.model.WeaponPermissions;
import com.us.archangel.weapon.service.WeaponService;

import java.util.List;

public class WeaponDeleteEvent  extends MessageHandler {
    @Override
    public void handle() {
        boolean canDeleteWeapons = this.client.getHabbo().hasPermissionRight(WeaponPermissions.DELETE);

        if (!canDeleteWeapons) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.generic.not_allowed"));
            return;
        }

        WeaponService.getInstance().deleteById(this.packet.readInt());

        List<WeaponModel> weaponModels = WeaponService.getInstance().getAll();
        this.client.sendResponse(new WeaponListComposer(weaponModels));
    }
}
