package com.us.nova.feature.betacode.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.nova.betacode.entity.BetaCodeEntity;
import com.us.nova.betacode.service.BetaCodeService;
import com.us.nova.core.RandomString;
import com.us.nova.feature.betacode.packets.outgoing.BetaCodeListComposer;

public class BetaCodeGenerateEvent extends MessageHandler {
    static protected String BETA_CODE_GENERATE_PERMISSION = "beta-code-generate";

    @Override
    public void handle() {
        boolean canGenerateBetaCode = this.client.getHabbo().getHabboInfo().getPermissionGroup().hasPermissionRight(BETA_CODE_GENERATE_PERMISSION, false);

        if (!canGenerateBetaCode) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.beta-code.generate.not-allowed"));
        }

        String betaCode = RandomString.generateRandomString();

        BetaCodeEntity betaCodeEntity = new BetaCodeEntity();
        betaCodeEntity.setCode(betaCode);
        betaCodeEntity.setCreatedAt((int) (System.currentTimeMillis() / 1000L));

        BetaCodeService.getInstance().create(betaCodeEntity);

        this.client.sendResponse(new BetaCodeListComposer());
    }
}
