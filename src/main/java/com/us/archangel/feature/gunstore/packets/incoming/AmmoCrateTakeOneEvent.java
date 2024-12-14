package com.us.archangel.feature.gunstore.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.ammo.model.AmmoModel;
import com.us.archangel.ammo.service.AmmoService;
import com.us.archangel.corp.enums.CorpIndustry;
import com.us.archangel.store.enums.StoreProductType;
import com.us.archangel.store.models.StoreProductModel;

public class AmmoCrateTakeOneEvent extends MessageHandler {
    @Override
    public void handle () {
        if (!this.client.getHabbo().getPlayer().isWorking()) {
            return;
        }

        if (this.client.getHabbo().getPlayer().getCorp().getIndustry() != CorpIndustry.GunStore) {
            return;
        }

        int ammoId = this.packet.readInt();

        AmmoModel ammo = AmmoService.getInstance().getById(ammoId);

        if (ammo == null) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.ammo_crate.item_not_found"));
            return;
        }

        StoreProductModel ammoProduct = new StoreProductModel(ammo.getId(), StoreProductType.AMMO);
        this.client.getHabbo().getInventory().getStoreShiftComponent().addProduct(ammoProduct);

        this.client.getHabbo().shout(Emulator.getTexts().getValue("roleplay.ammo_crate.product_added").replace(":item", ammo.getDisplayName()));
    }
}
