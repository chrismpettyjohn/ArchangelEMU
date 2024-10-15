package com.us.roleplay.messages.incoming.room;

import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.roleplay.messages.outgoing.room.RoomListUsersComposer;

public class RoomListUsersEvent extends MessageHandler {
    @Override
    public void handle() {
        Room room = this.client.getHabbo().getRoomUnit().getRoom();
        if (room == null) {
            return;
        }
        this.client.sendResponse(new RoomListUsersComposer(room));
    }
}
