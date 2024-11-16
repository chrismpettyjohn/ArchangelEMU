package com.us.archangel.feature.gang.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.gang.packets.outgoing.GangRoleQueryListComposer;
import com.us.archangel.feature.gang.packets.outgoing.GangRoleQueryOneComposer;
import com.us.archangel.gang.model.GangModel;
import com.us.archangel.gang.model.GangRoleModel;
import com.us.archangel.gang.service.GangRoleService;
import com.us.archangel.gang.service.GangService;

import java.util.List;

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

        List<GangRoleModel> gangRoleList = GangRoleService.getInstance().getByGangId(gangId);

        GangRoleModel gangRole = new GangRoleModel();
        gangRole.setGangId(gangId);
        gangRole.setOrderId(gangRoleList.size() + 1);
        gangRole.setName(this.packet.readString());
        gangRole.setCanInvite(this.packet.readBoolean());
        gangRole.setCanKick(this.packet.readBoolean());

        GangRoleModel savedGangRole = GangRoleService.getInstance().create(gangRole);

        this.client.sendResponse(new GangRoleQueryOneComposer(savedGangRole.getId()));
        this.client.sendResponse(new GangRoleQueryListComposer(savedGangRole.getGangId()));
    }
}