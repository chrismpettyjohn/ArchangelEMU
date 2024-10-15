package com.us.archangel.feature.corp.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.roleplay.commands.corp.CorpSuperHireCommand;
import com.us.archangel.feature.corp.packets.outgoing.CorpInfoComposer;

public class CorpInfoQueryEvent extends MessageHandler {
    @Override
    public void handle() {
        Integer corpID = this.packet.readInt();

        if (corpID == 0) {
            return;
        }

        this.client.sendResponse(new CorpInfoComposer(corpID));
    }

    public static class CorpSuperHireEvent extends MessageHandler {
        @Override
        public void handle() {
            String targetedUsername = this.packet.readString();
            String corpID = String.valueOf(this.packet.readInt());
            String corpPositionID = String.valueOf(this.packet.readInt());

            new CorpSuperHireCommand().handle(this.client, new String[] {null, targetedUsername, corpID, corpPositionID });
        }
    }
}