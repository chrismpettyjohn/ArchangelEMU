package com.us.archangel.feature.store.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.store.enums.StoreProductOfferStatus;
import com.us.archangel.store.models.StoreProductOfferModel;
import com.us.archangel.store.service.StoreProductOfferService;

public class RejectStoreProductOfferEvent extends MessageHandler {
    @Override
    public void handle () {
        int offerId = this.packet.readInt();

        StoreProductOfferModel offerModel = StoreProductOfferService.getInstance().getById(offerId);

        if (offerModel == null) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.store_offer.not_found"));
            return;
        }

        if (offerModel.getRecipientUserId() != this.client.getHabbo().getHabboInfo().getId()) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.store_offer.not_found"));
            return;
        }

        if (offerModel.getOfferStatus() != StoreProductOfferStatus.PENDING) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.store_offer.not_found"));
            return;
        }

        offerModel.setOfferStatus(StoreProductOfferStatus.REJECTED);
        StoreProductOfferService.getInstance().update(offerModel.getId(), offerModel);
    }
}
