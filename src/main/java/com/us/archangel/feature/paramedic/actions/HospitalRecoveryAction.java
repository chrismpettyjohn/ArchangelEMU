package com.us.archangel.feature.paramedic.actions;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.us.archangel.feature.hospital.interactions.InteractionHospitalBed;
import com.us.archangel.room.enums.RoomType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HospitalRecoveryAction implements Runnable {

    public static final int HEALTH_PER_CYCLE = 5;

    private final Habbo habbo;
    private boolean isHealing = true; // Ensures no further cycles run after healing completes

    @Override
    public void run() {

        if (!isHealing) {
            return;
        }

        if (this.habbo.getRoomUnit().getRoom() == null) {
            isHealing = false;
            return;
        }

        if (!this.habbo.getRoomUnit().getRoom().getRoomInfo().getTags().contains(RoomType.HOSPITAL)) {
            this.habbo.shout(Emulator.getTexts().getValue("roleplay.hospital.cancel_recovery"));
            isHealing = false;
            return;
        }

        if (this.habbo.getRoomUnit().getCurrentItem().getBaseItem().getInteractionType().getType() == InteractionHospitalBed.class) {            this.habbo.shout(Emulator.getTexts().getValue("roleplay.hospital.cancel_recovery"));
            isHealing = false;
            return;
        }

        if (this.habbo.getPlayer().getHealthNow() >= this.habbo.getPlayer().getHealthMax()) {
            onHealingComplete();
            return;
        }

        this.habbo.getPlayer().addHealth(HEALTH_PER_CYCLE);

        this.habbo.shout(Emulator.getTexts()
                .getValue("roleplay.hospital.progress_recovery")
                .replace(":healthNow", String.valueOf(this.habbo.getPlayer().getHealthNow()))
                .replace(":healthMax", String.valueOf(this.habbo.getPlayer().getHealthMax()))
        );

        Emulator.getThreading().run(this, 2500);
    }

    private void onHealingComplete() {
        this.habbo.shout(Emulator.getTexts().getValue("roleplay.hospital.finish_recovery"));
        isHealing = false;
    }
}
