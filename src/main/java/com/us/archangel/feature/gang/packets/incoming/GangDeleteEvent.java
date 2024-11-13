package com.us.archangel.feature.gang.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.permissions.Permission;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.gang.packets.outgoing.GangQueryListComposer;
import com.us.archangel.gang.model.GangModel;
import com.us.archangel.gang.service.GangService;

public class GangDeleteEvent extends MessageHandler {
    @Override
    public void handle() {
        boolean canEditAllGangs = this.client.getHabbo().hasPermissionRight(Permission.ACC_GANGS_EDIT_ALL);

        int gangId = this.packet.readInt();

        GangModel gang = GangService.getInstance().getById(gangId);

        if (gang.getUserId() != this.client.getHabbo().getHabboInfo().getId() && !canEditAllGangs) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.generic.not_allowed"));
            return;
        }


        this.client.sendResponse(new GangQueryListComposer());
    }
}