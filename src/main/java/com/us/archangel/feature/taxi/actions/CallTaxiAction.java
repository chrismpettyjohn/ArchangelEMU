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

@RequiredArgsConstructor
public class CallTaxiAction extends ManagedTask<CallTaxiAction> {

    @NonNull
    private final Habbo habbo;
    @NonNull
    private final Room targetRoom;

    @Override
    public void cycle() {
        if (!isRunning()) return;

        if (habbo.getRoomUnit().getRoom() != null &&
                habbo.getRoomUnit().getRoom().getRoomInfo().getId() == targetRoom.getRoomInfo().getId()) {
            stop();
            return;
        }

        int taxiFee = Integer.parseInt(Emulator.getConfig().getValue("roleplay.taxi.fee", "20"));
        boolean hasFreeTaxiPermission = habbo.hasPermissionRight(Permission.ACC_NAVIGATOR_SHOW_ALL);

        if (!hasFreeTaxiPermission && habbo.getHabboInfo().getCredits() < taxiFee) {
            habbo.whisper(Emulator.getTexts().getValue("roleplay.taxi.cant_afford"));
            stop();
            return;
        }

        if (!targetRoom.getRoomInfo().getTags().contains(RoomType.TAXI)) {
            habbo.whisper(Emulator.getTexts().getValue("roleplay.taxi.not_available")
                    .replace(":roomname", targetRoom.getRoomInfo().getName()));
            stop();
            return;
        }

        int taxiDelay = hasFreeTaxiPermission ? 0 :
                Integer.parseInt(Emulator.getConfig().getValue("roleplay.taxi.delay_secs", "20"));
        long arrivesAt = System.currentTimeMillis() / 1000 + taxiDelay;

        if (!hasFreeTaxiPermission) {
            habbo.getHabboInfo().setCredits(habbo.getHabboInfo().getCredits() - taxiFee);
            habbo.getClient().sendResponse(new CreditBalanceComposer(habbo));
            habbo.getClient().sendResponse(new UserRoleplayStatsChangeComposer(habbo));
        }

        habbo.getClient().sendResponse(new TaxiDispatchedComposer(targetRoom.getRoomInfo().getId(), arrivesAt));

        schedule(() -> {
            if (!isRunning()) return;
            habbo.shout(Emulator.getTexts().getValue("roleplay.taxi.picked_up")
                    .replace(":fee", String.valueOf(taxiFee)));
            habbo.goToRoom(targetRoom.getRoomInfo().getId(), () -> {
                habbo.shout(Emulator.getTexts().getValue("roleplay.taxi.arrived"));
            });
        }, taxiDelay);
    }
}