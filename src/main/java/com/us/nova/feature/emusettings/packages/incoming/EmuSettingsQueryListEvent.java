package com.us.nova.feature.emusettings.packages.incoming;

import com.eu.habbo.habbohotel.permissions.Permission;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.nova.feature.emusettings.packages.outgoing.EmuSettingsQueryListComposer;

public class EmuSettingsQueryListEvent extends MessageHandler {

    @Override
    public void handle() {
        if (!this.client.getHabbo().hasPermissionRight(Permission.ACC_MANAGE_GAME_SETTINGS)) {
            return;
        }
        this.client.sendResponse(new EmuSettingsQueryListComposer());
    }

}
