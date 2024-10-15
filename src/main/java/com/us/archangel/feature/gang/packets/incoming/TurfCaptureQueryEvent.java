package com.us.archangel.feature.gang.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.gang.packets.outgoing.TurfCaptureTimeLeftComposer;

public class TurfCaptureQueryEvent extends MessageHandler {
    @Override
    public void handle() {
        this.client.sendResponse(new TurfCaptureTimeLeftComposer(this.client.getHabbo().getRoomUnit().getRoom()));
    }
}