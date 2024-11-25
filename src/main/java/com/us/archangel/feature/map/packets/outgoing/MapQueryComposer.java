package com.us.archangel.feature.map.packets.outgoing;

import com.eu.habbo.Emulator;
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

        Map<Room, List<Room>> roomMap = new LinkedHashMap<>();
        roomMap.put(startingRoom, new ArrayList<>());
        traverseRooms(startingRoom, roomMap, 1);

        appendMapData(message, roomMap);
        return message;
    }

    private void traverseRooms(Room room, Map<Room, List<Room>> roomMap, int depth) {
        if (depth >= 2) {
            return;
        }

        for (RoomItem item : room.getRoomItemManager().getItemsOfType(InteractionInstantTeleport.class)) {
            InteractionInstantTeleport teleport = (InteractionInstantTeleport) item;

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
        List<Room> rooms = new ArrayList<>(roomMap.keySet());
        message.appendInt(rooms.size());

        // Ensure starting room is at index 0
        if (rooms.size() > 1 && !rooms.get(0).equals(this.startingRoom)) {
            Collections.swap(rooms, 0, rooms.indexOf(this.startingRoom));
        }

        // Calculate coordinates for all rooms relative to the starting room
        Map<Room, int[]> roomCoordinates = calculateRoomCoordinates(roomMap);

        for (Room room : rooms) {
            int[] coords = roomCoordinates.get(room);
            String roomData = String.join(";",
                    String.valueOf(room.getRoomInfo().getId()),
                    room.getRoomInfo().getName(),
                    String.valueOf(coords[0]),
                    String.valueOf(coords[1]),
                    String.valueOf(calculateRoomSize(room)));

            message.appendString(roomData);
        }
    }

    private Map<Room, int[]> calculateRoomCoordinates(Map<Room, List<Room>> roomMap) {
        Map<Room, int[]> coordinates = new HashMap<>();
        coordinates.put(startingRoom, new int[]{0, 0}); // Starting room at center

        Queue<Room> queue = new LinkedList<>();
        queue.offer(startingRoom);

        while (!queue.isEmpty()) {
            Room currentRoom = queue.poll();
            int[] currentCoords = coordinates.get(currentRoom);

            for (Room connectedRoom : roomMap.get(currentRoom)) {
                if (!coordinates.containsKey(connectedRoom)) {
                    int[] newCoords = calculateRelativeCoordinates(currentRoom, connectedRoom);
                    coordinates.put(connectedRoom, new int[]{
                            currentCoords[0] + newCoords[0],
                            currentCoords[1] + newCoords[1]
                    });
                    queue.offer(connectedRoom);
                }
            }
        }

        return coordinates;
    }

    private int[] calculateRelativeCoordinates(Room fromRoom, Room toRoom) {
        RoomItem teleport = fromRoom.getRoomItemManager().getItemsOfType(InteractionInstantTeleport.class)
                .stream()
                .filter(item -> ((InteractionInstantTeleport) item).getTargetRoomId() == toRoom.getRoomInfo().getId())
                .findFirst()
                .orElse(null);

        if (teleport == null) {
            return new int[]{0, 0}; // Default to center if no teleport found
        }

        int x = teleport.getCurrentPosition().getX();
        int y = teleport.getCurrentPosition().getY();

        // Get the dimensions of the room
        int maxX = fromRoom.getLayout().getMapSizeX();
        int maxY = fromRoom.getLayout().getMapSizeY();

        // Calculate the center point of the room
        int centerX = maxX / 2;
        int centerY = maxY / 2;

        // Calculate relative position using centerX and centerY
        if (y < centerY) {
            return new int[]{0, -1}; // Top
        } else if (y > centerY) {
            return new int[]{0, 1}; // Bottom
        } else if (x < centerX) {
            return new int[]{-1, 0}; // Left
        } else if (x > centerX) {
            return new int[]{1, 0}; // Right
        } else {
            return new int[]{0, 0}; // Default to center if teleport is at the center
        }
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