package com.us.archangel.feature.map.packets.outgoing;

import com.eu.habbo.habbohotel.items.interactions.InteractionTeleport;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.items.entities.RoomItem;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class MapQueryComposer extends MessageComposer {

    private final Room startingRoom;

    // This method is called to compose the map.
    @Override
    protected ServerMessage composeInternal() {
        // Create a new map.
        Map<Room, List<Room>> map = new HashMap<>();
        map.put(startingRoom, new ArrayList<>());

        // Recursively traverse the rooms starting from the starting room.
        traverseRooms(startingRoom, map);

        // Create a new ServerMessage.
        ServerMessage message = new ServerMessage();

        message.init(Outgoing.mapQueryComposer);

        // Append the map data to the message.
        appendMapData(message, map);

        // Return the message.
        return message;
    }

    // This method recursively traverses the rooms starting from the given room.
    private void traverseRooms(Room room, Map<Room, List<Room>> map) {
        HashSet<RoomItem> teleports = room.getRoomItemManager().getItemsOfType(InteractionTeleport.class);

        for (RoomItem teleport : teleports) {
            Room targetRoom = teleport.getRoom();

            if (targetRoom != null && !map.containsKey(targetRoom)) {
                map.put(targetRoom, new ArrayList<>());
                map.get(room).add(targetRoom);
                traverseRooms(targetRoom, map);
            }
        }
    }

    // This method appends the map data to the message.
    private void appendMapData(ServerMessage message, Map<Room, List<Room>> map) {
        message.appendString(this.startingRoom.getRoomInfo().getName()); // Added this line

        message.appendInt(map.size());

        for (Map.Entry<Room, List<Room>> entry : map.entrySet()) {
            Room room = entry.getKey();
            List<Room> connectedRooms = entry.getValue();

            message.appendString(room.getRoomInfo().getName());

            // Calculate the room's XY based on the teleporter positions.
            int[] xy = calculateRoomXY(room);
            message.appendInt(xy[0]);
            message.appendInt(xy[1]);

            message.appendInt(connectedRooms.size());

            for (Room connectedRoom : connectedRooms) {
                message.appendString(connectedRoom.getRoomInfo().getName());

                // Calculate the connected room's XY based on the teleporter positions.
                int[] connectedRoomXY = calculateRoomXY(connectedRoom);
                message.appendInt(connectedRoomXY[0]);
                message.appendInt(connectedRoomXY[1]);
            }
        }
    }

    // This method calculates the room's XY based on the teleporter positions.
    private int[] calculateRoomXY(Room room) {
        HashSet<RoomItem> teleports = room.getRoomItemManager().getItemsOfType(InteractionTeleport.class);

        if (teleports.isEmpty()) {
            return new int[] { 0, 0 };
        }

        int totalX = 0;
        int totalY = 0;
        for (RoomItem teleport : teleports) {
            totalX += teleport.getCurrentPosition().getX();
            totalY += teleport.getCurrentPosition().getY();
        }
        int averageX = totalX / teleports.size();
        int averageY = totalY / teleports.size();

        return new int[] { averageX, averageY };
    }
}
