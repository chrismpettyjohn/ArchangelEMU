package com.us.roleplay.messages.incoming.gang;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.gang.commands.GangLeaveCommand;

public class GangLeaveEvent  extends MessageHandler {
    @Override
    public void handle() {
        new GangLeaveCommand().handle(this.client, new String[] {,});
    }
}