package com.us.archangel.feature.gunstore.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.corp.enums.CorpIndustry;
import com.us.archangel.weapon.model.WeaponModel;
import com.us.archangel.weapon.service.WeaponService;
import com.us.roleplay.users.enums.StoreProductEnum;
import com.us.roleplay.users.models.StoreProduct;

public class WeaponVendingMachineTakeOneEvent  extends MessageHandler {
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

        StoreProduct weaponProduct = new StoreProduct(weapon.getId(), StoreProductEnum.WEAPON);
        this.client.getHabbo().getInventory().getStoreShiftComponent().addProduct(weaponProduct);

        this.client.getHabbo().shout(Emulator.getTexts().getValue("roleplay.weapon_vending_machine.product_added").replace(":item", weapon.getDisplayName()));
    }
}
