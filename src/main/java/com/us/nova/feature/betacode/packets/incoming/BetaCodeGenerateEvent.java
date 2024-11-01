package com.us.nova.feature.betacode.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.nova.betacode.entity.BetaCodeEntity;
import com.us.nova.betacode.service.BetaCodeService;
import com.us.nova.core.RandomString;
import com.us.nova.feature.betacode.packets.outgoing.BetaCodeListComposer;

public class BetaCodeGenerateEvent extends MessageHandler {
    @Override
    public void handle() {
        String betaCode = RandomString.generateRandomString();

        BetaCodeEntity betaCodeEntity = new BetaCodeEntity();
        betaCodeEntity.setCode(betaCode);
        betaCodeEntity.setCreatedAt((int) (System.currentTimeMillis() / 1000L));

        BetaCodeService.getInstance().create(betaCodeEntity);

        this.client.sendResponse(new BetaCodeListComposer());
    }
}
