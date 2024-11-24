package com.us.archangel.feature.taxi.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.permissions.Permission;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.users.CreditBalanceComposer;
import com.us.archangel.feature.taxi.actions.CallTaxiAction;
import com.us.archangel.feature.taxi.packets.outgoing.TaxiDispatchedComposer;
import com.us.archangel.room.enums.RoomType;

public class CallTaxiEvent extends MessageHandler {
    @Override
    public void handle() {
        int roomID = this.packet.readInt();

        if (this.client.getHabbo().getRoomUnit().getRoom() != null && this.client.getHabbo().getRoomUnit().getRoom().getRoomInfo().getId() == roomID ) {
            return;
        }

        Room targetedRoom = Emulator.getGameEnvironment().getRoomManager().getRoom(roomID);

        if (targetedRoom == null) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.taxi.not_found"));
            return;
        }

        if (!this.client.getHabbo().getPlayer().canInteract()) return;

        if (this.client.getHabbo().getRoomUnit().getRoom() != null &&
                this.client.getHabbo().getRoomUnit().getRoom().getRoomInfo().getId() == targetedRoom.getRoomInfo().getId()) {
            return;
        }

        int taxiFee = Integer.parseInt(Emulator.getConfig().getValue("roleplay.taxi.fee", "20"));
        boolean hasFreeTaxiPermission = this.client.getHabbo().hasPermissionRight(Permission.ACC_NAVIGATOR_SHOW_ALL);

        if (!hasFreeTaxiPermission && this.client.getHabbo().getHabboInfo().getCredits() < taxiFee) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.taxi.cant_afford"));
            return;
        }

        if (!targetedRoom.getRoomInfo().getTags().contains(RoomType.TAXI)) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.taxi.not_available")
                    .replace(":roomname", targetedRoom.getRoomInfo().getName()));
            return;
        }

        int taxiDelay = hasFreeTaxiPermission ? 0 : Emulator.getConfig().getInt("roleplay.taxi.delay_secs");
        long arrivesAt = System.currentTimeMillis() / 1000 + taxiDelay;

        if (!hasFreeTaxiPermission) {
            this.client.getHabbo().getHabboInfo().setCredits(this.client.getHabbo().getHabboInfo().getCredits() - taxiFee);
            this.client.getHabbo().getClient().sendResponse(new CreditBalanceComposer(this.client.getHabbo()));
        }

        this.client.getHabbo().getClient().sendResponse(new TaxiDispatchedComposer(targetedRoom.getRoomInfo().getId(), arrivesAt));


        CallTaxiAction callTaxiAction = new CallTaxiAction(this.client.getHabbo(), targetedRoom.getRoomInfo().getId(), taxiDelay, taxiFee);
        this.client.getHabbo().getPlayer().setTask(callTaxiAction);
    }
}