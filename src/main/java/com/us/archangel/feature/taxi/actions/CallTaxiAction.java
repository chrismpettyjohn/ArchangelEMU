package com.us.archangel.feature.taxi.actions;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.us.nova.core.ManagedTask;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CallTaxiAction extends ManagedTask<CallTaxiAction> {
    public static final int TAXI_WAITING_EFFECT = 237;

    @NonNull
    private final Habbo habbo;
    private final int roomId;
    private final int taxiFee;
    private final int startX;
    private final int startY;

    private boolean effectApplied = false;
    private boolean taskScheduled = false;

    @Override
    public void cycle() {
        if (!this.isRunning()) return;
        
        // If already scheduled the teleport, just wait
        if (taskScheduled) return;

        // If user has moved, cancel taxi without refund
        if (hasUserMoved()) {
            habbo.shout(Emulator.getTexts().getValue("roleplay.taxi.cancelled"));
            if (effectApplied) {
                habbo.getRoomUnit().setEffectId(0);
            }
            this.stop();
            return;
        }

        // Apply waiting effect if not already applied
        if (!effectApplied) {
            habbo.getRoomUnit().setEffectId(TAXI_WAITING_EFFECT);
            effectApplied = true;
        }

        // Schedule the teleport only once
        if (!taskScheduled) {
            taskScheduled = true;
            schedule(() -> {
                if (!this.isRunning()) return;

                // Final position check before teleporting
                if (hasUserMoved()) {
                    habbo.shout(Emulator.getTexts().getValue("roleplay.taxi.cancelled"));
                    if (effectApplied) {
                        habbo.getRoomUnit().setEffectId(0);
                    }
                    this.stop();
                    return;
                }

                // Remove effect before teleporting
                if (effectApplied) {
                    habbo.getRoomUnit().setEffectId(0);
                    effectApplied = false;
                }
                
                habbo.shout(Emulator.getTexts().getValue("roleplay.taxi.picked_up")
                        .replace(":fee", String.valueOf(this.taxiFee)));
                        
                habbo.goToRoom(this.roomId, () -> {
                    habbo.shout(Emulator.getTexts().getValue("roleplay.taxi.arrived"));
                });
                
                this.stop();
            }, Emulator.getConfig().getInt("roleplay.taxi.delay_secs", 5));
        }
    }

    private boolean hasUserMoved() {
        return habbo.getRoomUnit().getCurrentPosition().getX() != startX || 
               habbo.getRoomUnit().getCurrentPosition().getY() != startY;
    }
}