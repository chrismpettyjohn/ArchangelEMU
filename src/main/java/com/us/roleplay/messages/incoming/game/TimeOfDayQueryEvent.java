package com.us.roleplay.messages.incoming.game;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.roleplay.messages.outgoing.game.TimeOfDayComposer;

public class TimeOfDayQueryEvent extends MessageHandler {
    @Override
    public void handle() {
        this.client.sendResponse(new TimeOfDayComposer());

    }
}