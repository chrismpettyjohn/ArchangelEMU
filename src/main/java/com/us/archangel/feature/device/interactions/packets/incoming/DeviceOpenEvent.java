package com.us.archangel.feature.device.interactions.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.rooms.items.entities.RoomItem;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.corp.enums.CorpIndustry;
import com.us.archangel.corp.model.CorpModel;
import com.us.archangel.feature.device.interactions.InteractionPhone;
import com.us.archangel.feature.police.interactions.InteractionPoliceLaptop;
import com.us.archangel.feature.device.interactions.InteractionUsable;
import com.us.archangel.feature.device.interactions.packets.outgoing.DeviceOpenComposer;

public class DeviceOpenEvent extends MessageHandler {
    @Override
    public void handle() {
        int itemID = this.packet.readInt();

        RoomItem itemFromInventory = this.client.getHabbo().getInventory().getItemsComponent().getHabboItem(itemID);
        RoomItem itemFromHotBar = this.client.getHabbo().getInventory().getHotBarComponent().getItems().get(itemID);
        RoomItem item = (itemFromInventory != null) ? itemFromInventory : itemFromHotBar;

        if (item == null) {
            this.client.getHabbo().shout("invalid item");
            return;
        }

        if (!InteractionUsable.class.isAssignableFrom(item.getBaseItem().getInteractionType().getType())) {
            this.client.getHabbo().shout("device is not usable");
            return;
        }

        if (item.getBaseItem().getInteractionType().getType() == InteractionPhone.class) {
            this.client.getHabbo().shout(Emulator.getTexts().getValue("roleplay.phone.take_out"));
            this.client.getHabbo().getRoomUnit().giveEffect(InteractionPhone.PHONE_EFFECT_ID, -1);
        }

        if (item.getBaseItem().getInteractionType().getType() == InteractionPoliceLaptop.class) {
            CorpModel corp = this.client.getHabbo().getPlayer().getCorp();

            if (corp == null) {
                this.client.getHabbo().whisper(Emulator.getTexts().getValue("generic.roleplay.unemployed"));
                return;
            }

            if (corp.getIndustry() != CorpIndustry.Police) {
                this.client.getHabbo().whisper(Emulator.getTexts().getValue("generic.roleplay.police_only"));
                return;
            }

            if (!this.client.getHabbo().getPlayer().isWorking()) {
                this.client.getHabbo().whisper(Emulator.getTexts().getValue("generic.roleplay.must_be_working"));
                return;
            }
            this.client.getHabbo().shout(Emulator.getTexts().getValue("roleplay.police_laptop.take_out"));
            this.client.getHabbo().getRoomUnit().giveEffect(InteractionPoliceLaptop.LAPTOP_EFFECT_ID, -1);
        }

        this.client.sendResponse(new DeviceOpenComposer(item));
    }
}