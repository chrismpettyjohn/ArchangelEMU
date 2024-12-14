package com.us.archangel.feature.store.packets.outgoing;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.archangel.store.models.StoreProductOfferModel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OfferStoreProductComposer extends MessageComposer {

    private final StoreProductOfferModel offer;

    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.offerStoreProductComposer);

        return this.response;
    }
}
