package com.us.archangel.feature.store.packets.outgoing;

import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.archangel.ammo.model.AmmoModel;
import com.us.archangel.ammo.service.AmmoService;
import com.us.archangel.store.enums.StoreProductType;
import com.us.archangel.store.models.StoreProductModel;
import com.us.archangel.weapon.model.WeaponModel;
import com.us.archangel.weapon.service.WeaponService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class StoreShiftInventoryDataComposer extends MessageComposer {

    private final Habbo habbo;

    @Override
    protected ServerMessage composeInternal() {
        List<StoreProductModel> storeProductModelModels = this.habbo.getInventory().getStoreShiftComponent().getStoreProductModels();

        this.response.init(Outgoing.storeShiftInventoryDataComposer);

        this.response.appendInt(storeProductModelModels.size());

        for (StoreProductModel productModel : storeProductModelModels) {
            String productName = String.valueOf(productModel.getId());

            if (productModel.getType() == StoreProductType.AMMO) {
                AmmoModel ammoModel = AmmoService.getInstance().getById(productModel.getId());
                productName = ammoModel.getDisplayName();
            }

            if (productModel.getType() == StoreProductType.WEAPON) {
                WeaponModel weaponModel = WeaponService.getInstance().getById(productModel.getId());
                productName = weaponModel.getDisplayName();
            }
            this.response.appendString(productModel.getId() + ";" + productModel.getType() + ";" + productName);
        }

        return this.response;
    }
}
