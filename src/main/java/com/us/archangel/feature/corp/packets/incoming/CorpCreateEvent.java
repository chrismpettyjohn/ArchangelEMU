package com.us.archangel.feature.corp.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.permissions.Permission;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.corp.entity.CorpEntity;
import com.us.archangel.corp.enums.CorpIndustry;
import com.us.archangel.corp.enums.CorpSector;
import com.us.archangel.corp.mapper.CorpMapper;
import com.us.archangel.corp.model.CorpModel;
import com.us.archangel.corp.service.CorpService;
import com.us.archangel.feature.corp.packets.outgoing.CorpInfoComposer;
import com.us.archangel.feature.corp.packets.outgoing.CorpListComposer;

public class CorpCreateEvent extends MessageHandler {
    @Override
    public void handle() {
        boolean canCreateCorps = this.client.getHabbo().hasPermissionRight(Permission.ACC_CORPS_EDIT_ALL);

        if (!canCreateCorps) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.generic.not_allowed"));
            return;
        }

        CorpEntity corp = new CorpEntity();
        corp.setDisplayName(this.packet.readString());
        corp.setDescription(this.packet.readString());
        corp.setBadge(this.packet.readString());
        corp.setUserId(this.packet.readInt());
        corp.setRoomId(this.packet.readInt());
        corp.setSector(CorpSector.fromString(this.packet.readString()));
        corp.setIndustry(CorpIndustry.fromString(this.packet.readString()));
        corp.setCreatedAt((int) (System.currentTimeMillis() / 1000L));
        
        Room room = Emulator.getGameEnvironment().getRoomManager().getRoom(corp.getRoomId());

        if (room == null) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.room_not_yours"));
            return;
        }

        if (room.getRoomInfo().getOwnerInfo().getId() != this.client.getHabbo().getHabboInfo().getId() && !canCreateCorps) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.room_not_yours"));
            return;
        }

        CorpModel savedCorp = CorpService.getInstance().create(CorpMapper.toModel(corp));

        this.client.sendResponse(new CorpInfoComposer(savedCorp.getId()));
        this.client.sendResponse(new CorpListComposer());
    }
}
