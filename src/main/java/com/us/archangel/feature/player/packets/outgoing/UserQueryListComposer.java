package com.us.archangel.feature.player.packets.outgoing;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.HabboInfo;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.archangel.player.model.PlayerModel;
import com.us.archangel.player.service.PlayerService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class UserQueryListComposer extends MessageComposer {
    public final int page;
    public final String query;
    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.userQueryListComposer);

        List<HabboInfo> matchingHabbos =  Emulator.getGameEnvironment().getHabboManager().queryHabbos(this.page, this.query);

        this.response.appendInt(matchingHabbos.size());

        for (HabboInfo habboInfo : matchingHabbos) {
            PlayerModel rpStats = PlayerService.getInstance().getByUserID(habboInfo.getId());
            this.response.appendString(
                    habboInfo.getId()
                    + ";" + habboInfo.getUsername()
                    + ";" + habboInfo.getLook()
                    + ";" + rpStats.getCorpId()
                    + ";" + rpStats.getCorpRoleId()
                    + ";" + (rpStats.getGangId() != null ? rpStats.getGangId() : -1)
                    + ";" + (rpStats.getGangRoleId() != null ? rpStats.getGangRoleId() : -1)
            );
        }

        return this.response;
    }
}
