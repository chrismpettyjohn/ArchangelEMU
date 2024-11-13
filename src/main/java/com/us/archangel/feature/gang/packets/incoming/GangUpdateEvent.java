package com.us.archangel.feature.gang.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.permissions.Permission;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.gang.packets.outgoing.GangQueryOneComposer;
import com.us.archangel.gang.model.GangModel;
import com.us.archangel.gang.service.GangService;

public class GangUpdateEvent extends MessageHandler {
    @Override
    public void handle() {
        boolean canEditAllGangs = this.client.getHabbo().hasPermissionRight(Permission.ACC_GANGS_EDIT_ALL);

        int gangId = this.packet.readInt();

        GangModel gang = GangService.getInstance().getById(gangId);

        if (gang.getUserId() != this.client.getHabbo().getHabboInfo().getId() && !canEditAllGangs) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.generic.not_allowed"));
            return;
        }

        gang.setDisplayName(this.packet.readString());
        gang.setDescription(this.packet.readString());
        gang.setBadge(this.packet.readString());
        gang.setUserId(this.packet.readInt());
        gang.setRoomId(this.packet.readInt());
        gang.update();

        Room room = Emulator.getGameEnvironment().getRoomManager().getRoom(gang.getRoomId());

        if (room == null) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.room_not_yours"));
            return;
        }

        if (room.getRoomInfo().getOwnerInfo().getId() != this.client.getHabbo().getHabboInfo().getId() && !canEditAllGangs) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.room_not_yours"));
            return;
        }

        this.client.sendResponse(new GangQueryOneComposer(gang.getId()));
    }
}