package com.us.archangel.feature.taxi.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.taxi.actions.CallTaxiAction;

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


        CallTaxiAction callTaxiAction = new CallTaxiAction(this.client.getHabbo(), targetedRoom);
        this.client.getHabbo().getPlayer().setTask(callTaxiAction);
    }
}