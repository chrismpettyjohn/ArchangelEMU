package com.us.archangel.feature.map.packets.outgoing;

import com.eu.habbo.habbohotel.items.interactions.InteractionTeleport;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.items.entities.RoomItem;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.archangel.feature.wardrobe.interactions.InteractionInstantTeleport;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class MapQueryComposer extends MessageComposer {

    private final Room startingRoom;

    @Override
    protected ServerMessage composeInternal() {
        ServerMessage message = new ServerMessage();
        message.init(Outgoing.mapQueryComposer);

        Map<Room, List<Room>> roomMap = new HashMap<>();
        roomMap.put(startingRoom, new ArrayList<>());
        traverseRooms(startingRoom, roomMap);

        appendMapData(message, roomMap);
        return message;
    }

    private void traverseRooms(Room room, Map<Room, List<Room>> roomMap) {
        for (RoomItem teleport : room.getRoomItemManager().getItemsOfType(InteractionTeleport.class)) {
            Room targetRoom = teleport.getRoom();
            if (targetRoom != null && !roomMap.containsKey(targetRoom)) {
                roomMap.put(targetRoom, new ArrayList<>());
                roomMap.get(room).add(targetRoom);
                traverseRooms(targetRoom, roomMap);
            }
        }
    }

    private void appendMapData(ServerMessage message, Map<Room, List<Room>> roomMap) {
        message.appendInt(roomMap.size()); // Total rooms count

        for (Room room : roomMap.keySet()) {
            int[] coords = calculateRoomCoordinates(room);
            String roomData = String.join(";",
                    String.valueOf(room.getRoomInfo().getId()),
                    room.getRoomInfo().getName(),
                    String.valueOf(coords[0]),
                    String.valueOf(coords[1]),
                    String.valueOf(calculateRoomSize(room))); // Calculate size separately

            message.appendString(roomData);
        }
    }

    private int[] calculateRoomCoordinates(Room room) {
        Set<RoomItem> teleports = room.getRoomItemManager().getItemsOfType(InteractionInstantTeleport.class);
        if (teleports.isEmpty()) {
            return new int[]{0, 0};
        }

        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;

        for (RoomItem teleport : teleports) {
            int x = teleport.getCurrentPosition().getX();
            int y = teleport.getCurrentPosition().getY();

            minX = Math.min(minX, x);
            minY = Math.min(minY, y);
            maxX = Math.max(maxX, x);
            maxY = Math.max(maxY, y);
        }

        int centerX = (minX + maxX) / 2;
        int centerY = (minY + maxY) / 2;

        return new int[]{centerX, centerY};
    }

    private int calculateRoomSize(Room room) {
        Set<RoomItem> teleports = room.getRoomItemManager().getItemsOfType(InteractionInstantTeleport.class);
        if (teleports.isEmpty()) {
            return 0; // Or some default size
        }

        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;

        for (RoomItem teleport : teleports) {
            int x = teleport.getCurrentPosition().getX();
            int y = teleport.getCurrentPosition().getY();

            minX = Math.min(minX, x);
            minY = Math.min(minY, y);
            maxX = Math.max(maxX, x);
            maxY = Math.max(maxY, y);
        }

        int width = maxX - minX + 1; // +1 to include both min and max
        int height = maxY - minY + 1;

        return width * height; // Area of the room
    }
}
