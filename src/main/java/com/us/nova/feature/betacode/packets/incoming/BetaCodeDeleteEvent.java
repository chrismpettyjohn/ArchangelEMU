package com.us.nova.feature.betacode.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.nova.betacode.service.BetaCodeService;
import com.us.nova.feature.betacode.packets.outgoing.BetaCodeListComposer;

public class BetaCodeDeleteEvent extends MessageHandler {
    @Override
    public void handle() {
        int betaCodeId = this.packet.readInt();
        BetaCodeService.getInstance().deleteById(betaCodeId);
        this.client.sendResponse(new BetaCodeListComposer());
    }
}
