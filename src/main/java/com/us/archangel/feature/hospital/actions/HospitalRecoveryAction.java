package com.us.archangel.feature.hospital.actions;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.rooms.RoomTile;
import com.eu.habbo.habbohotel.rooms.items.entities.RoomItem;
import com.eu.habbo.habbohotel.users.Habbo;
import com.us.archangel.feature.hospital.interactions.InteractionHospitalBed;
import com.us.archangel.feature.player.packets.outgoing.UserRoleplayStatsChangeComposer;
import com.us.archangel.room.enums.RoomType;
import com.us.nova.core.NotificationHelper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class HospitalRecoveryAction implements Runnable {

    private final Habbo habbo;
    @Override
    public void run() {

        if (this.habbo.getRoomUnit().getRoom() == null) {
            return;
        }

        if (!this.habbo.getRoomUnit().getRoom().getRoomInfo().getTags().contains(RoomType.HOSPITAL)) {
            this.habbo.shout(Emulator.getTexts().getValue("roleplay.hospital.cancel_recovery"));
            return;
        }

        if (this.habbo.getRoomUnit().getCurrentItem().getBaseItem().getInteractionType().getType() != InteractionHospitalBed.class) {            this.habbo.shout(Emulator.getTexts().getValue("roleplay.hospital.cancel_recovery"));
            return;
        }

        if (this.habbo.getPlayer().getHealthNow() == this.habbo.getPlayer().getHealthMax()) {

            Optional<RoomItem> firstAvailableSeat = this.habbo.getRoomUnit().getRoom().getRoomItemManager().getCurrentItems().values().stream()
                    .filter(item -> item.getBaseItem().allowSit() && item.getRoom().canSitOrLayAt(item.getCurrentPosition()) && !item.getClass().equals(InteractionHospitalBed.class))
                    .findFirst();

            if (firstAvailableSeat.isEmpty()) {
                // teleport somewhere
                return;
            }

            habbo.getRoomUnit().setLocation(firstAvailableSeat.get().getCurrentPosition());
            this.habbo.shout(Emulator.getTexts().getValue("roleplay.hospital.finish_recovery"));

            return;
        }

        this.habbo.getPlayer().addHealth(Emulator.getConfig().getInt("roleplay.hospital.health_per_cycle"));

        NotificationHelper.sendRoom(this.habbo.getRoomUnit().getRoom().getRoomInfo().getId(), new UserRoleplayStatsChangeComposer(this.habbo));

        this.habbo.shout(Emulator.getTexts()
                .getValue("roleplay.hospital.progress_recovery")
                .replace(":healthNow", String.valueOf(this.habbo.getPlayer().getHealthNow()))
                .replace(":healthMax", String.valueOf(this.habbo.getPlayer().getHealthMax()))
        );

        Emulator.getThreading().run(this, 2500);
    }
}
