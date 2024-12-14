package com.us.archangel.feature.store.packets.outgoing;

import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.roleplay.users.models.StoreProduct;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class StoreShiftInventoryDataComposer extends MessageComposer {

    private final Habbo habbo;

    @Override
    protected ServerMessage composeInternal() {
        List<StoreProduct> storeProductModels = this.habbo.getInventory().getStoreShiftComponent().getStoreProducts();

        this.response.init(Outgoing.playerWeaponListComposer);

        for (StoreProduct productModel : storeProductModels) {
            this.response.appendString(productModel.getId() + ";" + productModel.getType());
        }

        return this.response;
    }
}
