package com.us.archangel.feature.hospital.actions;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.list.LayCommand;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomTile;
import com.eu.habbo.habbohotel.rooms.entities.RoomRotation;
import com.eu.habbo.habbohotel.rooms.items.entities.RoomItem;
import com.eu.habbo.habbohotel.users.Habbo;
import com.us.archangel.feature.hospital.interactions.InteractionHospitalBed;
import com.us.archangel.player.enums.PlayerAction;
import com.us.archangel.room.enums.RoomType;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class TeleportHospitalAction implements Runnable {

    private final Habbo habbo;
    private Room hospitalRoom = Emulator.getGameEnvironment().getRoomManager().getActiveRooms().stream().filter(Room::isHospital).findFirst().orElse(null);

    @Override
    public void run() {
        if (this.habbo.getRoomUnit() == null) {
            return;
        }

        if (this.hospitalRoom == null) {
            throw new RuntimeException("no hospitals found");
        }

        if (this.habbo.getPlayer().getEscortingPlayerId() != null) {
            this.habbo.getPlayer().setCurrentAction(PlayerAction.None);
            this.habbo.getPlayer().setEscortingPlayerId(null);
        }

        this.habbo.shout(Emulator.getTexts().getValue("roleplay.user_is_dead"));
        this.habbo.getRoomUnit().setCanWalk(false);

        int deadTeleportDelay = Emulator.getConfig().getInt("roleplay.dead.delay", 10000);

        this.habbo.shout(Emulator.getTexts().getValue("roleplay.dead.teleporting_to_hospital_delay").replace(":seconds", String.valueOf(deadTeleportDelay / 1000)));

        Emulator.getThreading().run(() -> {
            if (this.habbo.getRoomUnit().getRoom().isHospital()) {
                this.hospitalRoom = this.habbo.getRoomUnit().getRoom();
                this.teleportAndLayOnHospitalBed();
                return;
            }

            habbo.goToRoom(this.hospitalRoom.getRoomInfo().getId(), this::teleportAndLayOnHospitalBed);
        }, deadTeleportDelay);
    }

    private void teleportAndLayOnHospitalBed() {
        Collection<RoomItem> hospitalBedItems = this.hospitalRoom.getRoomItemManager().getItemsOfType(InteractionHospitalBed.class);
        for (RoomItem hospitalBedItem : hospitalBedItems) {
            List<RoomTile> hospitalBedRoomTiles = hospitalBedItem.getOccupyingTiles(this.hospitalRoom.getLayout());
            RoomTile firstAvailableHospitalBedTile = hospitalBedRoomTiles.isEmpty() ? null : hospitalBedRoomTiles.get(0);

            if (firstAvailableHospitalBedTile == null || !canLayOnBed(hospitalBedItem, firstAvailableHospitalBedTile)) {
                continue;
            }

            RoomTile pillow = switch (hospitalBedItem.getRotation()) {
                case 0, 4 -> this.hospitalRoom.getLayout().getTile(hospitalBedItem.getCurrentPosition().getX(), hospitalBedItem.getCurrentPosition().getY());
                case 2, 8 -> this.hospitalRoom.getLayout().getTile(hospitalBedItem.getCurrentPosition().getX(), hospitalBedItem.getCurrentPosition().getY());
                default -> firstAvailableHospitalBedTile;
            };

            habbo.getRoomUnit().setLocation(pillow);
            break;
        }
    }

    private boolean canLayOnBed(RoomItem bed, RoomTile tile) {
        return bed != null && bed.getBaseItem().allowLay() && this.hospitalRoom.canLayAt(tile);
    }

}
