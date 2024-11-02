package com.us.archangel.feature.corp.packets.outgoing;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.archangel.player.model.PlayerModel;
import com.us.archangel.player.service.PlayerService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CorpEmployeeListComposer extends MessageComposer {
    private final int corpID;

    @Override
    protected ServerMessage composeInternal() {
        List<PlayerModel> habboRoleplayStatsList = PlayerService.getInstance().getByCorpId(corpID);

        this.response.init(Outgoing.corpEmployeeListComposer);
        this.response.appendInt(this.corpID);
        this.response.appendInt(habboRoleplayStatsList.size());

        for (PlayerModel habboRoleplayStats : habboRoleplayStatsList) {
            Habbo habbo = Emulator.getGameEnvironment().getHabboManager().getHabbo(habboRoleplayStats.getUserId());
            this.response.appendString(
                    habboRoleplayStats.getUserId()
                            + ";" + habbo.getHabboInfo().getUsername()
                            + ";" + habbo.getHabboInfo().getLook()
                            + ";" + habboRoleplayStats.getCorpRoleId()
                            + ";" + habboRoleplayStats.getCorpRole().getDisplayName());
        }

        return this.response;
    }
}
