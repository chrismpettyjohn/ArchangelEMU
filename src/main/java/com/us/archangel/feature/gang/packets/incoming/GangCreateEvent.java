package com.us.archangel.feature.gang.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.permissions.Permission;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.gang.packets.outgoing.GangQueryListComposer;
import com.us.archangel.feature.gang.packets.outgoing.GangQueryOneComposer;
import com.us.archangel.gang.model.GangModel;
import com.us.archangel.gang.service.GangService;

public class GangCreateEvent extends MessageHandler {
    @Override
    public void handle() {
        if (this.client.getHabbo().getPlayer().getGangId() != null) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.gang.already_in_gang"));
            return;
        }

        GangModel gang = new GangModel();
        gang.setDisplayName(this.packet.readString());
        gang.setDescription(this.packet.readString());
        gang.setBadge(this.packet.readString());
        gang.setUserId(this.packet.readInt());
        gang.setRoomId(this.packet.readInt());

        boolean canEditAllGangs = this.client.getHabbo().hasPermissionRight(Permission.ACC_GANGS_EDIT_ALL);

        Room room = Emulator.getGameEnvironment().getRoomManager().getRoom(gang.getRoomId());

        if (room == null) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.room_not_yours"));
            return;
        }

        if (room.getRoomInfo().getOwnerInfo().getId() != this.client.getHabbo().getHabboInfo().getId() && !canEditAllGangs) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.room_not_yours"));
            return;
        }

        GangModel savedGang = GangService.getInstance().create(gang);

        this.client.sendResponse(new GangQueryOneComposer(savedGang.getId()));
        this.client.sendResponse(new GangQueryListComposer());
    }
}