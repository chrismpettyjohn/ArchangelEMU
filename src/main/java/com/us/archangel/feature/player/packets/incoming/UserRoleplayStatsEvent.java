package com.us.archangel.feature.player.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.player.packets.outgoing.UserRoleplayStatsChangeComposer;

public class UserRoleplayStatsEvent extends MessageHandler {
    @Override
    public void handle() {
        int roomId = this.packet.readInt();
        Habbo targetedUser = Emulator.getGameEnvironment().getHabboManager().getHabbo(roomId);

        if (targetedUser == null) {
            return;
        }

        this.client.sendResponse(new UserRoleplayStatsChangeComposer(targetedUser));

    }
}