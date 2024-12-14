package com.us.archangel.feature.gunstore.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.threading.runnables.RoomUnitGiveHanditem;
import com.us.archangel.corp.enums.CorpIndustry;
import com.us.archangel.feature.store.packets.outgoing.StoreShiftInventoryDataComposer;
import com.us.archangel.weapon.context.WeaponContext;
import com.us.archangel.weapon.model.WeaponModel;
import com.us.archangel.weapon.service.WeaponService;
import com.us.archangel.store.enums.StoreProductType;
import com.us.archangel.store.models.StoreProductModel;

public class WeaponVendingMachineTakeEvent extends MessageHandler {
    @Override
    public void handle () {
        if (!this.client.getHabbo().getPlayer().isWorking()) {
            return;
        }

        if (this.client.getHabbo().getPlayer().getCorp().getIndustry() != CorpIndustry.GunStore) {
            return;
        }

        int weaponId = this.packet.readInt();

        WeaponModel weapon = WeaponService.getInstance().getById(weaponId);

        if (weapon == null) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.weapon_vending_machine.item_not_found"));
            return;
        }

        StoreProductModel weaponProduct = new StoreProductModel(weapon.getId(), StoreProductType.WEAPON);
        this.client.getHabbo().getInventory().getStoreShiftComponent().addProduct(weaponProduct);

        if (weapon.getEquipHandItem() > 0) {
            Emulator.getThreading().run(new RoomUnitGiveHanditem(this.client.getHabbo().getRoomUnit(), this.client.getHabbo().getRoomUnit().getRoom(), weapon.getEquipHandItem()));
        }

        if (weapon.getEquipEffect() > 0) {
            this.client.getHabbo().getRoomUnit().giveEffect(weapon.getEquipEffect(), -1);
        }

        this.client.getHabbo().shout(Emulator.getTexts().getValue("roleplay.weapon_vending_machine.product_added").replace(":item", weapon.getDisplayName()));

        this.client.sendResponse(new StoreShiftInventoryDataComposer(this.client.getHabbo()));
    }
}
