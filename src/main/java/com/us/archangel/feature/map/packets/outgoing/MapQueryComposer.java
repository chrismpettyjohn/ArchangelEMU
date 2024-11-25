package com.us.archangel.feature.map.packets.outgoing;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.items.interactions.InteractionTeleport;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomLayout;
import com.eu.habbo.habbohotel.rooms.RoomTile;
import com.eu.habbo.habbohotel.rooms.constants.RoomTileState;
import com.eu.habbo.habbohotel.rooms.items.entities.RoomItem;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.eu.habbo.threading.runnables.teleport.TeleportAction;
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
        traverseRooms(startingRoom, roomMap, 1); // Start with depth 0

        appendMapData(message, roomMap);
        return message;
    }

    private void traverseRooms(Room room, Map<Room, List<Room>> roomMap, int depth) {
        if (depth >= 4) {
            return;
        }

        for (RoomItem item : room.getRoomItemManager().getItemsOfType(InteractionInstantTeleport.class)) {
            InteractionInstantTeleport teleport = (InteractionInstantTeleport) item;

            // Resolve the target teleport
            teleport = (InteractionInstantTeleport) TeleportAction.resolveTeleportTarget(teleport);

            int targetRoomId = teleport.getTargetRoomId();
            Room targetRoom = Emulator.getGameEnvironment().getRoomManager().getRoom(targetRoomId);
            if (targetRoom != null && !roomMap.containsKey(targetRoom)) {
                roomMap.put(targetRoom, new ArrayList<>());
                roomMap.get(room).add(targetRoom);
                traverseRooms(targetRoom, roomMap, depth + 1);
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
        RoomLayout layout = room.getLayout();
        if (layout == null) {
            return 0;
        }

        int maxX = 0;
        int maxY = 0;

        for (int x = 0; x < layout.getMapSizeX(); x++) {
            for (int y = 0; y < layout.getMapSizeY(); y++) {
                RoomTile tile = layout.getTile((short) x, (short) y);
                if (tile != null && tile.getState() != RoomTileState.INVALID) {
                    maxX = Math.max(maxX, x);
                    maxY = Math.max(maxY, y);
                }
            }
        }

        int width = maxX + 1;
        int height = maxY + 1;

        return width * height;
    }
}