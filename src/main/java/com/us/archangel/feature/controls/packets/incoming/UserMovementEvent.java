package com.us.archangel.feature.controls.packets.incoming;

import com.eu.habbo.habbohotel.rooms.entities.units.RoomUnit;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.incoming.MessageHandler;

public class UserMovementEvent extends MessageHandler {

    @Override
    public void handle() {
        if (!this.client.getHabbo().getPlayer().canWalk()) {
            return;
        }

        String movementDirectionKey = this.packet.readString();

        if (movementDirectionKey == null) {
            return;
        }

        MovementDirection direction = MovementDirection.fromKey(movementDirectionKey);

        if (direction == null) {
            return;
        }

        Habbo habbo = this.client.getHabbo();
        RoomUnit roomUnit = habbo.getRoomUnit();

        if (direction == MovementDirection.STOP) {
            roomUnit.stopMoving();
        } else {
            roomUnit.startMoving(direction);
        }
    }
}
