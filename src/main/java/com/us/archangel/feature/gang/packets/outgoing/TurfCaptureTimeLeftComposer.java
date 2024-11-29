package com.us.archangel.feature.gang.packets.outgoing;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.HashMap;

@AllArgsConstructor
public class TurfCaptureTimeLeftComposer extends MessageComposer {
    private final Room room;

    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.turfCaptureTimeLeftComposer);

        HashMap<Integer, Integer> gangsInRoom = getGangsInRoom();

        Habbo capturingHabbo = Emulator.getGameEnvironment().getHabboManager().getHabbo(this.room.getRoomTurfManager().getCapturingUserId());

        this.response.appendString(String.valueOf(this.room.getRoomTurfManager().getCaptureFinishesAt()));
        this.response.appendBoolean(this.room.getRoomTurfManager().getCapturePausedAt() == 0);
        this.response.appendInt(this.room.getRoomTurfManager().getCapturingUserId());
        this.response.appendInt(capturingHabbo.getPlayer().getGangId());
        this.response.appendInt(gangsInRoom.size());
        gangsInRoom.forEach((gangId, userCount) -> {
            this.response.appendString(gangId + ";" + userCount);
        });
        return this.response;
    }

    private HashMap<Integer, Integer> getGangsInRoom() {
        Collection<Habbo> usersInRoom = this.room.getRoomUnitManager().getCurrentHabbos().values();

        HashMap<Integer, Integer> gangsInRoom = new HashMap<>();

        for (Habbo user : usersInRoom) {
            Integer gangID = user.getPlayer().getGang() != null
                    ? user.getPlayer().getGang().getId()
                    : 0;

            if (gangsInRoom.containsKey(gangID)) {
                gangsInRoom.put(gangID, gangsInRoom.get(gangID) + 1);
                continue;
            }

            gangsInRoom.put(gangID, 1);
        }
        return gangsInRoom;
    }
}
