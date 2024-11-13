package com.us.archangel.feature.gang.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.permissions.Permission;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.gang.packets.outgoing.GangQueryOneComposer;
import com.us.archangel.gang.model.GangModel;
import com.us.archangel.gang.model.GangRoleModel;
import com.us.archangel.gang.service.GangRoleService;
import com.us.archangel.gang.service.GangService;

public class GangRoleUpdateEvent extends MessageHandler {
    @Override
    public void handle() {
        boolean canEditAllGangs = this.client.getHabbo().hasPermissionRight(Permission.ACC_GANGS_EDIT_ALL);

        int gangRoleId = this.packet.readInt();

        GangRoleModel gangRole = GangRoleService.getInstance().getById(gangRoleId);
        GangModel gang = GangService.getInstance().getById(gangRole.getGangId());

        if (gang.getUserId() != this.client.getHabbo().getHabboInfo().getId() && !canEditAllGangs) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.generic.not_allowed"));
            return;
        }

        gangRole.setOrderId(this.packet.readInt());
        gangRole.setName(this.packet.readString());
        gangRole.setCanKick(this.packet.readBoolean());
        gangRole.setCanInvite(this.packet.readBoolean());
        gangRole.update();

        this.client.sendResponse(new GangQueryOneComposer(gang.getId()));
    }
}