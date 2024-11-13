package com.us.archangel.feature.gang.packets.outgoing;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.HabboInfo;
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
public class GangMemberQueryListComposer extends MessageComposer {
    private final int gangId;

    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.gangMemberQueryListComposer);

        List<PlayerModel> members = PlayerService.getInstance().getByGangId(this.gangId);

        this.response.appendInt(members.size());

        for (PlayerModel member : members) {
            GangRoleModel gangRole = GangRoleService.getInstance().getById(member.getGangRoleId());
            HabboInfo habbo = Emulator.getGameEnvironment().getHabboManager().getOfflineHabboInfo(member.getId());
            this.response.appendString(String.format("%s;%s;%s;%s;%s", habbo.getId(), habbo.getUsername(), habbo.getLook(), gangRole.getId(), gangRole.getName()));
        }

        return this.response;
    }

}
