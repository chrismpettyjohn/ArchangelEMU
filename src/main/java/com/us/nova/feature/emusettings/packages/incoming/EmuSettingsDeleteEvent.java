package com.us.nova.feature.emusettings.packages.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.permissions.Permission;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.nova.feature.emusettings.packages.outgoing.EmuSettingsQueryListComposer;

public class EmuSettingsDeleteEvent extends MessageHandler {

    @Override
    public void handle() {
        if (!this.client.getHabbo().hasPermissionRight(Permission.ACC_MANAGE_GAME_SETTINGS)) {
            return;
        }

        Emulator.getConfig().deleteByKey(this.packet.readString());

        this.client.sendResponse(new EmuSettingsQueryListComposer());
    }
}
