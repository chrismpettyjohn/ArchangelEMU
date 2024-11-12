package com.us.archangel.feature.room.packets.outgoing;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.nova.room.models.RoomPermissionModel;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class RoomQueryListComposer extends MessageComposer {

    private final Habbo habbo;

    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.roomQueryListComposer);

        List<Room> rooms = habbo.hasPermissionRight(RoomPermissionModel.READ)
                ? Emulator.getGameEnvironment().getRoomManager().getAllRooms()
                : Emulator.getGameEnvironment().getRoomManager().getRoomsForHabbo(this.habbo);

        this.response.appendInt(rooms.size());
        for (Room room : rooms) {
            this.response.appendString(room.getRoomInfo().getId() + ";" + room.getRoomInfo().getName());
        }
        return this.response;
    }
}