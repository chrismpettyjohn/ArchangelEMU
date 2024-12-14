package com.us.archangel.feature.store.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.corp.enums.CorpIndustry;
import com.us.archangel.feature.store.packets.outgoing.OfferStoreProductComposer;
import com.us.archangel.store.entity.StoreProductOfferEntity;
import com.us.archangel.store.mapper.StoreProductOfferMapper;
import com.us.archangel.store.models.StoreProductModel;
import com.us.archangel.store.models.StoreProductOfferModel;
import com.us.archangel.store.service.StoreProductOfferService;

public class CreateStoreProductOfferEvent extends MessageHandler {
    @Override
    public void handle () {
        if (!this.client.getHabbo().getPlayer().isWorking()) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.sell_store_product.not_allowed"));
            return;
        }

        if (this.client.getHabbo().getPlayer().getCorp().getIndustry() != CorpIndustry.GunStore) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.sell_store_product.not_allowed"));
            return;
        }

        int productId = this.packet.readInt();

        StoreProductModel productModel = this.client.getHabbo().getInventory().getStoreShiftComponent().getProductById(productId);

        if (productModel == null) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.sell_store_product.not_found"));
            return;
        }

        int targetUserId = this.packet.readInt();

        Habbo targetUser = this.client.getHabbo().getRoomUnit().getRoom().getRoomUnitManager().getRoomHabboById(targetUserId);

        if (targetUser == null) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.sell_store_product.user_not_found"));
            return;
        }

        StoreProductOfferEntity productOfferEntity = new StoreProductOfferEntity();
        productOfferEntity.setEmployeePlayerId(this.client.getHabbo().getHabboInfo().getId());
        productOfferEntity.setRecipientPlayerId(targetUserId);
        productOfferEntity.setStoreProductId(productId);
        productOfferEntity.setStoreProductType(productModel.getType());
        StoreProductOfferModel productOfferModel = StoreProductOfferMapper.toModel(productOfferEntity);

        StoreProductOfferService.getInstance().create(productOfferModel);
        targetUser.getClient().sendResponse(new OfferStoreProductComposer(productOfferModel));
    }
}
