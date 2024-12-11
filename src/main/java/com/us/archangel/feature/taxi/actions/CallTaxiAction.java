package com.us.archangel.feature.taxi.actions;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.items.interactions.InteractionTeleport;
import com.eu.habbo.habbohotel.rooms.RoomTile;
import com.eu.habbo.habbohotel.rooms.entities.units.RoomUnit;
import com.eu.habbo.habbohotel.rooms.items.entities.RoomItem;
import com.eu.habbo.habbohotel.users.Habbo;
import com.us.archangel.feature.hospital.interactions.InteractionHospitalBed;
import com.us.archangel.feature.taxi.packets.incoming.CallTaxiEvent;
import com.us.nova.core.ManagedTask;
import com.us.archangel.feature.taxi.interactions.InteractionTaxiStand;
import com.eu.habbo.habbohotel.rooms.Room;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Optional;

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
                    CallTaxiAction.teleportToNearbyTaxiStand(habbo);
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

    public static void teleportToNearbyTaxiStand(Habbo habbo) {
        Optional<RoomItem> taxiStand = habbo.getRoomUnit().getRoom().getRoomItemManager().getCurrentItems()
                .values().stream()
                .filter(item -> item.getClass().isAssignableFrom(InteractionTaxiStand.class))
                .findFirst();

        if (taxiStand.isEmpty()) {
            return;
        }

        // Get position from taxi stand
        short targetX = taxiStand.get().getCurrentPosition().getX();
        short targetY = taxiStand.get().getCurrentPosition().getY();

        // Get closest unoccupied adjacent tile
        // true parameter enables diagonal tiles for more possible positions
        RoomTile nearbyTile = habbo.getRoomUnit().getClosestAdjacentTile(targetX, targetY, false);

        // Only teleport if we found a valid tile
        if (nearbyTile != null) {
            habbo.getRoomUnit().setLocation(nearbyTile);
        }
    }
}