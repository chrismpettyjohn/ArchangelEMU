package com.us.archangel.feature.store.packets.outgoing;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.archangel.ammo.model.AmmoModel;
import com.us.archangel.ammo.service.AmmoService;
import com.us.archangel.store.enums.StoreProductType;
import com.us.archangel.store.models.StoreProductOfferModel;
import com.us.archangel.weapon.model.WeaponModel;
import com.us.archangel.weapon.service.WeaponService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OfferStoreProductComposer extends MessageComposer {

    private final StoreProductOfferModel offer;

    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.offerStoreProductComposer);


        String productName = String.valueOf(this.offer.getProductId());
        String productKey = String.valueOf(this.offer.getProductId());

        if (this.offer.getProductType() == StoreProductType.AMMO) {
            AmmoModel ammoModel = AmmoService.getInstance().getById(this.offer.getProductId());
            productName = ammoModel.getDisplayName();
            productKey = ammoModel.getUniqueName();
        }

        if (this.offer.getProductType() == StoreProductType.WEAPON) {
            WeaponModel weaponModel = WeaponService.getInstance().getById(this.offer.getProductId());
            productName = weaponModel.getDisplayName();
            productKey = weaponModel.getUniqueName();
        }

        this.response.appendInt(this.offer.getId());
        this.response.appendInt(this.offer.getProductId());
        this.response.appendString(this.offer.getProductType().toString());
        this.response.appendInt(this.offer.getProductCost());
        this.response.appendString(productName);
        this.response.appendString(productKey);

        return this.response;
    }
}
