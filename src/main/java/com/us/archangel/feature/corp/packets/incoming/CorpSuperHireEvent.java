package com.us.archangel.feature.corp.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.corp.commands.CorpSuperHireCommand;

public class CorpSuperHireEvent extends MessageHandler {
    @Override
    public void handle() {
        String targetedUsername = this.packet.readString();
        String corpID = String.valueOf(this.packet.readInt());
        String corpPositionID = String.valueOf(this.packet.readInt());

        new CorpSuperHireCommand().handle(this.client, new String[] {null, targetedUsername, corpID, corpPositionID });
    }
}