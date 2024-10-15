package com.us.roleplay.messages.incoming.gang;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.roleplay.messages.outgoing.gang.TurfCaptureTimeLeftComposer;

public class TurfCaptureQueryEvent extends MessageHandler {
    @Override
    public void handle() {
        this.client.sendResponse(new TurfCaptureTimeLeftComposer(this.client.getHabbo().getRoomUnit().getRoom()));
    }
}