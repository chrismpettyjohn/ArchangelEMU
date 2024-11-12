package com.us.archangel.feature.corp.packets.outgoing;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.habbohotel.users.HabboInfo;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.archangel.corp.enums.CorpIndustry;
import com.us.archangel.corp.enums.CorpSector;
import com.us.archangel.corp.model.CorpModel;
import com.us.archangel.corp.service.CorpService;
import com.us.archangel.player.model.PlayerModel;
import com.us.archangel.player.service.PlayerService;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
public class CorpInfoComposer extends MessageComposer {
    private final int corpID;

    @Override
    protected ServerMessage composeInternal() {
        CorpModel matchingCorp = CorpService.getInstance().getById(this.corpID);
        Room room = Emulator.getGameEnvironment().getRoomManager().getRoom(matchingCorp.getRoomId());
        HabboInfo owner = Emulator.getGameEnvironment().getHabboManager().getOfflineHabboInfo(matchingCorp.getUserId());
        List<PlayerModel> employees = PlayerService.getInstance().getByCorpId(matchingCorp.getId());
        this.response.init(Outgoing.corpInfoComposer);
        this.response.appendInt(matchingCorp.getId());;
        this.response.appendInt(matchingCorp.getUserId());
        this.response.appendString(owner.getUsername());
        this.response.appendString(owner.getLook());
        this.response.appendInt(matchingCorp.getRoomId());
        this.response.appendString(room.getRoomInfo().getName());
        this.response.appendString(matchingCorp.getDisplayName());
        this.response.appendString(matchingCorp.getDescription());
        this.response.appendString(matchingCorp.getBadge());
        this.response.appendInt(employees.size());
        this.response.appendString(matchingCorp.getIndustry().toString());
        this.response.appendString(matchingCorp.getSector().toString());
        this.response.appendString(LocalDateTime.ofInstant(Instant.ofEpochSecond(matchingCorp.getCreatedAt()), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
        return this.response;
    }
}