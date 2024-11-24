package com.us.archangel.feature.taxi.actions;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.us.nova.core.ManagedTask;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CallTaxiAction extends ManagedTask<CallTaxiAction> {

    @NonNull
    private final Habbo habbo;
    private final int roomId;
    private final int taxiDelay;
    private final int taxiFee;

    protected boolean isScheduled = false;

    @Override
    public void cycle() {
        schedule(() -> {
            if (!this.isRunning()) return;
            if (this.isScheduled) return;
            this.isScheduled = true;
            habbo.shout(Emulator.getTexts().getValue("roleplay.taxi.picked_up")
                    .replace(":fee", String.valueOf(this.taxiFee)));
            habbo.goToRoom(this.roomId, () -> {
                habbo.shout(Emulator.getTexts().getValue("roleplay.taxi.arrived"));
            });
        }, this.taxiDelay);
    }
}