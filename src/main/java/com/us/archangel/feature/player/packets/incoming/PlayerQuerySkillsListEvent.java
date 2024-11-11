package com.us.archangel.feature.player.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.player.packets.outgoing.PlayerQuerySkillsListComposer;

public class PlayerQuerySkillsListEvent extends MessageHandler  {
    @Override
    public void handle() {
        int userID = this.packet.readInt();
        this.client.sendResponse(new PlayerQuerySkillsListComposer(userID));
    }
}
