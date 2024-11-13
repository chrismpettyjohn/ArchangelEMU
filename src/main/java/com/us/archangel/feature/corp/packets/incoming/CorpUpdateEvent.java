package com.us.archangel.feature.corp.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.permissions.Permission;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.corp.entity.CorpEntity;
import com.us.archangel.corp.enums.CorpIndustry;
import com.us.archangel.corp.enums.CorpSector;
import com.us.archangel.corp.mapper.CorpMapper;
import com.us.archangel.corp.service.CorpService;
import com.us.archangel.feature.corp.packets.outgoing.CorpInfoComposer;

public class CorpUpdateEvent extends MessageHandler {
    @Override
    public void handle() {
        boolean canUpdateCorps = this.client.getHabbo().hasPermissionRight(Permission.ACC_CORPS_EDIT_ALL);

        if (!canUpdateCorps) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.generic.not_allowed"));
            return;
        }

        CorpEntity corpEntity = CorpMapper.toEntity(CorpService.getInstance().getById(this.packet.readInt()));
        corpEntity.setDisplayName(this.packet.readString());
        corpEntity.setDescription(this.packet.readString());
        corpEntity.setBadge(this.packet.readString());
        corpEntity.setUserId(this.packet.readInt());
        corpEntity.setRoomId(this.packet.readInt());
        corpEntity.setSector(CorpSector.fromString(this.packet.readString()));
        corpEntity.setIndustry(CorpIndustry.fromString(this.packet.readString()));

        Room room = Emulator.getGameEnvironment().getRoomManager().getRoom(corpEntity.getRoomId());

        if (room == null) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.room_not_yours"));
            return;
        }

        if (room.getRoomInfo().getOwnerInfo().getId() != this.client.getHabbo().getHabboInfo().getId() && !canUpdateCorps) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.room_not_yours"));
            return;
        }

        CorpService.getInstance().update(corpEntity.getId(), CorpMapper.toModel(corpEntity));

        this.client.sendResponse(new CorpInfoComposer(corpEntity.getId()));
    }
}
