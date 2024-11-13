package com.us.archangel.feature.gang.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.gang.packets.outgoing.GangRoleQueryOneComposer;
import com.us.archangel.gang.model.GangModel;
import com.us.archangel.gang.model.GangRoleModel;
import com.us.archangel.gang.service.GangService;

public class GangRoleCreateEvent extends MessageHandler {
    @Override
    public void handle() {
        int gangId = this.packet.readInt();

        GangModel gang = GangService.getInstance().getById(gangId);

        if (gang == null) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.generic.not_allowed"));
            return;
        }

        if (gang.getUserId() != this.client.getHabbo().getHabboInfo().getId()) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.generic.not_allowed"));
            return;
        }

        GangRoleModel gangRole = new GangRoleModel();
        gangRole.setOrderId(this.packet.readInt());
        gangRole.setName(this.packet.readString());
        gangRole.setCanInvite(this.packet.readBoolean());
        gangRole.setCanKick(this.packet.readBoolean());
        gangRole.update();

        this.client.sendResponse(new GangRoleQueryOneComposer(gangRole.getId()));
    }
}