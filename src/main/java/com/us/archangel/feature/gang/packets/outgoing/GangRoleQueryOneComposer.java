package com.us.archangel.feature.gang.packets.outgoing;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.archangel.gang.model.GangRoleModel;
import com.us.archangel.gang.service.GangRoleService;
import com.us.archangel.player.model.PlayerModel;
import com.us.archangel.player.service.PlayerService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GangRoleQueryOneComposer extends MessageComposer {
    private final int gangRoleID;

    @Override
    protected ServerMessage composeInternal() {
        List<PlayerModel> players = PlayerService.getInstance().getByGangRoleId(this.gangRoleID);
        GangRoleModel gangRole = GangRoleService.getInstance().getById(this.gangRoleID);
        this.response.init(Outgoing.gangRoleQueryOneComposer);
        this.response.appendInt(gangRole.getId());
        this.response.appendInt(gangRole.getGangId());
        this.response.appendString(gangRole.getName());
        this.response.appendBoolean(gangRole.isCanInvite());
        this.response.appendBoolean(gangRole.isCanKick());
        this.response.appendInt(players.size());
        return this.response;
    }

}
