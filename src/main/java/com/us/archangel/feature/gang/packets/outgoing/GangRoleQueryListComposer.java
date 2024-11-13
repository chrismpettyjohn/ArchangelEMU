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
public class GangRoleQueryListComposer extends MessageComposer {
    private final int gangId;

    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.gangRoleQueryListComposer);

        List<GangRoleModel> gangRoles = GangRoleService.getInstance().getByGangId(this.gangId);

        this.response.appendInt(gangRoles.size());

        for (GangRoleModel gangRole : gangRoles) {
            List<PlayerModel> players = PlayerService.getInstance().getByGangRoleId(gangRole.getId());
            this.response.appendString(String.format("%s;%s;%s;%s;%s;%s;%s", gangRole.getId(), gangRole.getGangId(), gangRole.getOrderId(), gangRole.getName(), gangRole.isCanInvite(), gangRole.isCanKick(), players.size()));
        }

        return this.response;
    }

}
