package com.us.archangel.feature.taxi.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.permissions.Permission;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.items.entities.RoomItem;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.users.CreditBalanceComposer;
import com.us.archangel.feature.taxi.actions.CallTaxiAction;
import com.us.archangel.feature.taxi.interactions.InteractionTaxiStand;
import com.us.archangel.feature.taxi.packets.outgoing.TaxiDispatchedComposer;

import java.util.Optional;

public class CallTaxiEvent extends MessageHandler {
    @Override
    public void handle() {
        int roomID = this.packet.readInt();

        // Basic room validation
        Room targetedRoom = Emulator.getGameEnvironment().getRoomManager().getRoom(roomID);
        if (targetedRoom == null) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.taxi.not_found"));
            return;
        }

        // Check if trying to taxi to current room
        Room currentRoom = this.client.getHabbo().getRoomUnit().getRoom();
        if (currentRoom != null && currentRoom.getRoomInfo().getId() == roomID) {
            return;
        }

        if (!this.client.getHabbo().getPlayer().canInteract()) return;

        boolean hasFreeTaxiPermission = this.client.getHabbo().hasPermissionRight(Permission.ACC_NAVIGATOR_SHOW_ALL);

        // Check if the room has a taxi stand, bypass for staff with navigator perms
        boolean hasTaxiStand = hasFreeTaxiPermission;
        if (!hasTaxiStand) {
            for (var item : targetedRoom.getRoomItemManager().getCurrentItems().values()) {
                if (item.getBaseItem().getInteractionType().getType().isAssignableFrom(InteractionTaxiStand.class)) {
                    hasTaxiStand = true;
                    break;
                }
            }
        }

        if (!hasTaxiStand) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.taxi.not_available")
                    .replace(":roomname", targetedRoom.getRoomInfo().getName()));
            return;
        }

        // At this point we know the taxi is valid, handle payment and dispatch
        int taxiFee = Integer.parseInt(Emulator.getConfig().getValue("roleplay.taxi.fee", "20"));
        if (!hasFreeTaxiPermission) {
            if (this.client.getHabbo().getHabboInfo().getCredits() < taxiFee) {
                this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.taxi.cant_afford"));
                return;
            }
            
            // Deduct credits
            this.client.getHabbo().getHabboInfo().setCredits(this.client.getHabbo().getHabboInfo().getCredits() - taxiFee);
            this.client.getHabbo().getClient().sendResponse(new CreditBalanceComposer(this.client.getHabbo()));
        }

        // For staff, teleport immediately
        if (hasFreeTaxiPermission) {
            this.client.getHabbo().shout(Emulator.getTexts().getValue("roleplay.taxi.picked_up").replace(":fee", "0"));
            this.client.getHabbo().goToRoom(roomID, () -> {
                CallTaxiAction.teleportToNearbyTaxiStand(this.client.getHabbo());
                this.client.getHabbo().shout(Emulator.getTexts().getValue("roleplay.taxi.arrived"));
            });
            return;
        }

        // Regular users go through the waiting period
        int delaySeconds = Emulator.getConfig().getInt("roleplay.taxi.delay_secs", 5);
        this.client.getHabbo().shout(Emulator.getTexts().getValue("roleplay.taxi.called")
                .replace(":seconds", String.valueOf(delaySeconds)));

        // Store initial position for movement check
        int startX = this.client.getHabbo().getRoomUnit().getCurrentPosition().getX();
        int startY = this.client.getHabbo().getRoomUnit().getCurrentPosition().getY();

        CallTaxiAction callTaxiAction = new CallTaxiAction(
            this.client.getHabbo(), 
            targetedRoom.getRoomInfo().getId(), 
            taxiFee,
            startX,
            startY
        );
        
        this.client.getHabbo().getPlayer().setTask(callTaxiAction);
        callTaxiAction.start();
        
        this.client.getHabbo().getClient().sendResponse(new TaxiDispatchedComposer(
            targetedRoom.getRoomInfo().getId(), 
            System.currentTimeMillis() / 1000 + delaySeconds
        ));
    }
}