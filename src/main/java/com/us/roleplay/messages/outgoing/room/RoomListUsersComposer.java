package com.us.roleplay.messages.outgoing.room;

import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import lombok.AllArgsConstructor;

import java.util.Collection;

@AllArgsConstructor
public class RoomListUsersComposer extends MessageComposer {
    private final Room room;
    @Override
    protected ServerMessage composeInternal() {
        Collection<Habbo> habbosInRoom = this.room.getRoomUnitManager().getCurrentHabbos().values();
        this.response.init(Outgoing.roomListUsersComposer);
        this.response.appendInt(habbosInRoom.size());
        for (Habbo habbo : habbosInRoom) {
            this.response.appendString(habbo.getHabboInfo().getId() + ";" + habbo.getHabboInfo().getUsername());
        }
        return this.response;
    }
}