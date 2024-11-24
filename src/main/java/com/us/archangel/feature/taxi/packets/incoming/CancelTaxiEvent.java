package com.us.archangel.feature.taxi.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.nova.core.ManagedTask;

public class CancelTaxiEvent extends MessageHandler {
    @Override
    public void handle() {
        ManagedTask<?> currentTask = this.client.getHabbo().getPlayer().getCurrentTask();

        if (currentTask == null) {
            return;
        }

        if (!currentTask.getClass().isAssignableFrom(CallTaxiEvent.class)) {
            return;
        }

        this.client.getHabbo().getPlayer().setTask(null);
    }
}