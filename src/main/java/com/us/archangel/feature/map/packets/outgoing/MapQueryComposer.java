package com.us.archangel.feature.map.packets.outgoing;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomLayout;
import com.eu.habbo.habbohotel.rooms.items.entities.RoomItem;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.eu.habbo.threading.runnables.teleport.TeleportAction;
import com.us.archangel.feature.wardrobe.interactions.InteractionInstantTeleport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
public class MapQueryComposer extends MessageComposer {
    private final Room startingRoom;
    private final Map<Integer, Room> mappedRooms = new HashMap<>();

    @Override
    protected ServerMessage composeInternal() {
        try {
            if (startingRoom == null) return null;

            // Start with current room
            mappedRooms.put(startingRoom.getRoomInfo().getId(), startingRoom);

            // Map immediate connections only
            for (RoomItem item : startingRoom.getRoomItemManager().getItemsOfType(InteractionInstantTeleport.class)) {
                InteractionInstantTeleport teleport = (InteractionInstantTeleport) item;
                teleport = (InteractionInstantTeleport) TeleportAction.resolveTeleportTarget(teleport);
                
                int targetRoomId = teleport.getTargetRoomId();
                Room targetRoom = Emulator.getGameEnvironment().getRoomManager().getRoom(targetRoomId);
                
                if (targetRoom != null) {
                    mappedRooms.put(targetRoomId, targetRoom);
                }
            }

            // Create message
            ServerMessage message = new ServerMessage();
            message.init(Outgoing.mapQueryComposer);
            message.appendInt(mappedRooms.size());

            // Add starting room first
            appendRoom(message, startingRoom);

            // Add connected rooms
            for (Room room : mappedRooms.values()) {
                if (room.getRoomInfo().getId() != startingRoom.getRoomInfo().getId()) {
                    appendRoom(message, room);
                }
            }

            return message;

        } catch (Exception e) {
            log.error("Error in MapQueryComposer", e);
            return null;
        }
    }

    private void appendRoom(ServerMessage message, Room room) {
        try {
            RoomLayout layout = room.getLayout();
            if (layout == null) return;

            List<TeleportData> teleports = new ArrayList<>();

            // If this is the starting room, get all its teleports
            if (room.getRoomInfo().getId() == startingRoom.getRoomInfo().getId()) {
                for (RoomItem item : room.getRoomItemManager().getItemsOfType(InteractionInstantTeleport.class)) {
                    InteractionInstantTeleport teleport = (InteractionInstantTeleport) item;
                    teleport = (InteractionInstantTeleport) TeleportAction.resolveTeleportTarget(teleport);
                    int targetRoomId = teleport.getTargetRoomId();

                    if (mappedRooms.containsKey(targetRoomId)) {
                        teleports.add(new TeleportData(
                            teleport.getCurrentPosition().getX(),
                            teleport.getCurrentPosition().getY(),
                            0,
                            0,
                            targetRoomId
                        ));
                    }
                }
            }

            StringBuilder data = new StringBuilder()
                .append(room.getRoomInfo().getId()).append(";")
                .append(room.getRoomInfo().getName()).append(";")
                .append("0;0;") // x,y coordinates calculated by client
                .append(layout.getMapSizeX()).append(";")
                .append(layout.getMapSizeY()).append(";")
                .append(teleports.size());

            for (TeleportData tele : teleports) {
                data.append(";")
                    .append(tele.x).append(";")
                    .append(tele.y).append(";")
                    .append(tele.targetX).append(";")
                    .append(tele.targetY).append(";")
                    .append(tele.targetRoomId);
            }

            message.appendString(data.toString());

        } catch (Exception e) {
            log.error("Error appending room {}", room.getRoomInfo().getId(), e);
        }
    }

    private static class TeleportData {
        final int x;
        final int y;
        final int targetX;
        final int targetY;
        final int targetRoomId;

        TeleportData(int x, int y, int targetX, int targetY, int targetRoomId) {
            this.x = Math.max(0, x);
            this.y = Math.max(0, y);
            this.targetX = Math.max(0, targetX);
            this.targetY = Math.max(0, targetY);
            this.targetRoomId = targetRoomId;
        }
    }
}