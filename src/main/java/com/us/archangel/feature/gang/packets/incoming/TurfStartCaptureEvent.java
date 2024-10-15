package com.us.archangel.feature.gang.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.gang.action.CaptureTurfAction;

public class TurfStartCaptureEvent  extends MessageHandler {
    @Override
    public void handle() {
        Emulator.getThreading().run(new CaptureTurfAction(this.client.getHabbo().getRoomUnit().getRoom(), this.client.getHabbo().getRoomUnit().getCurrentPosition(), this.client.getHabbo()));
    }
}