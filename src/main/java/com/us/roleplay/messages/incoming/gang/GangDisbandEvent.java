package com.us.roleplay.messages.incoming.gang;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.gang.commands.GangDisbandCommand;

public class GangDisbandEvent extends MessageHandler {
    @Override
    public void handle() {
        new GangDisbandCommand().handle(this.client, new String[] {});
    }
}