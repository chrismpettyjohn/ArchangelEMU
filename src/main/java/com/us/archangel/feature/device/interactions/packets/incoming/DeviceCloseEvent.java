package com.us.archangel.feature.device.interactions.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.rooms.items.entities.RoomItem;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.device.interactions.InteractionPhone;
import com.us.archangel.feature.device.interactions.InteractionUsable;

public class DeviceCloseEvent extends MessageHandler {
    @Override
    public void handle() {
        int itemID = this.packet.readInt();
        RoomItem item = this.client.getHabbo().getInventory().getItemsComponent().getHabboItem(itemID);

        if (item == null) {
            return;
        }

        if (!InteractionUsable.class.isAssignableFrom(item.getBaseItem().getInteractionType().getType())) {
            return;
        }

        if (InteractionPhone.class.isAssignableFrom(item.getBaseItem().getInteractionType().getType())) {
            this.client.getHabbo().shout(Emulator.getTexts().getValue("roleplay.phone_put_away"));
            this.client.getHabbo().getRoomUnit().giveEffect(0, -1);
            return;
        }

        throw new RuntimeException("unsupported item");
    }
}