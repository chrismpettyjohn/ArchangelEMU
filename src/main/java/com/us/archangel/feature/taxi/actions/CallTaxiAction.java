package com.us.archangel.feature.taxi.actions;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.permissions.Permission;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.outgoing.users.CreditBalanceComposer;
import com.us.archangel.feature.player.packets.outgoing.UserRoleplayStatsChangeComposer;
import com.us.archangel.feature.taxi.packets.outgoing.TaxiDispatchedComposer;
import com.us.archangel.room.enums.RoomType;
import com.us.nova.core.ManagedTask;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@RequiredArgsConstructor
public class CallTaxiAction extends ManagedTask<CallTaxiAction> {

    @NonNull
    private final Habbo habbo;
    @NonNull
    private final Room targetRoom;

    private final AtomicBoolean cancelled = new AtomicBoolean(false);
    private ScheduledExecutorService executor;

    @Override
    public void cycle() {
        if (habbo.getRoomUnit().getRoom() != null && habbo.getRoomUnit().getRoom().getRoomInfo().getId() == targetRoom.getRoomInfo().getId()) {
            return;
        }

        int taxiFee = Integer.parseInt(Emulator.getConfig().getValue("roleplay.taxi.fee", "20"));
        boolean hasFreeTaxiPermission = habbo.hasPermissionRight(Permission.ACC_NAVIGATOR_SHOW_ALL);

        if (!hasFreeTaxiPermission) {
            if (habbo.getHabboInfo().getCredits() < taxiFee) {
                habbo.whisper(Emulator.getTexts().getValue("roleplay.taxi.cant_afford"));
                return;
            }

            if (!targetRoom.getRoomInfo().getTags().contains(RoomType.TAXI)) {
                habbo.whisper(Emulator.getTexts().getValue("roleplay.taxi.not_available")
                        .replace(":roomname", targetRoom.getRoomInfo().getName()));
                return;
            }
        }

        int taxiDelay = hasFreeTaxiPermission ? Integer.parseInt(Emulator.getConfig().getValue("roleplay.taxi.delay_secs", "20")) : 0;
        long arrivesAt = System.currentTimeMillis() / 1000 + taxiDelay;

        if (!hasFreeTaxiPermission) {
            habbo.getHabboInfo().setCredits(habbo.getHabboInfo().getCredits() - taxiFee);
            habbo.shout(Emulator.getTexts().getValue("roleplay.taxi.dispatched")
                    .replace(":roomName", targetRoom.getRoomInfo().getName())
                    .replace(":roomID", String.valueOf(targetRoom.getRoomInfo().getId())));
            habbo.getClient().sendResponse(new CreditBalanceComposer(habbo));
            habbo.getClient().sendResponse(new UserRoleplayStatsChangeComposer(habbo));
        }

        habbo.getClient().sendResponse(new TaxiDispatchedComposer(targetRoom.getRoomInfo().getId(), arrivesAt));

        executor = Executors.newSingleThreadScheduledExecutor();
        int countdownInterval = 5;

        for (int i = 1; i <= 4; i++) {
            int countdownSeconds = i * countdownInterval;
            int delaySeconds = taxiDelay - countdownSeconds;

            if (countdownSeconds <= taxiDelay) {
                executor.schedule(() -> {
                    if (!this.cancelled.get()) {
                        habbo.shout(Emulator.getTexts().getValue("roleplay.taxi.eta").replace(":seconds", String.valueOf(countdownSeconds)));
                    }
                }, delaySeconds, TimeUnit.SECONDS);
            }
        }

        executor.schedule(() -> {
            if (this.cancelled.get()) return;

            habbo.shout(Emulator.getTexts().getValue("roleplay.taxi.picked_up").replace(":fee", String.valueOf(taxiFee)));
            habbo.goToRoom(targetRoom.getRoomInfo().getId());

            executor.schedule(() -> {
                if (!this.cancelled.get()) {
                    habbo.shout(Emulator.getTexts().getValue("roleplay.taxi.arrived"));
                }
                executor.shutdown();
            }, 500, TimeUnit.MILLISECONDS);
        }, taxiDelay, TimeUnit.SECONDS);
    }
}
