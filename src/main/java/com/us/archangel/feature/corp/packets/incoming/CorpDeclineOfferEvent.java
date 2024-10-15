package com.us.archangel.feature.corp.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.corp.commands.CorpDeclineJobCommand;
import com.us.archangel.feature.corp.commands.CorpPromoteCommand;

public class CorpDeclineOfferEvent  extends MessageHandler {
    @Override
    public void handle() {
        String corpName = this.packet.readString();

        if (corpName == null) {
            return;
        }

        new CorpDeclineJobCommand().handle(this.client, new String[] {null, corpName});
    }

    public static class CorpPromoteUserEvent extends MessageHandler {
        @Override
        public void handle() {
            String targetedUsername = this.packet.readString();

            if (targetedUsername == null) {
                return;
            }

            new CorpPromoteCommand().handle(this.client, new String[] {null, targetedUsername});
        }
    }
}