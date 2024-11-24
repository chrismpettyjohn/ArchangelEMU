package com.us.archangel.feature.taxi.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.taxi.actions.CallTaxiAction;
import com.us.nova.core.ManagedTask;

public class CancelTaxiEvent extends MessageHandler {
    @Override
    public void handle() {
        ManagedTask<?> currentTask = this.client.getHabbo().getPlayer().getCurrentTask();

        if (currentTask == null) {
            return;
        }

        if (!(currentTask instanceof CallTaxiAction)) {
            return;
        }

        int taxiFee = Integer.parseInt(Emulator.getConfig().getValue("roleplay.taxi.fee", "20"));

        this.client.getHabbo().getHabboInfo().addCredits(taxiFee);
        this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.taxi_fee_refunded").replace("%fee%", Integer.toString(taxiFee)));
        this.client.getHabbo().getPlayer().setTask(null);
    }
}
