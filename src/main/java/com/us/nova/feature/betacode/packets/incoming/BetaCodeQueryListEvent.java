package com.us.nova.feature.betacode.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.nova.betacode.model.BetaCodePermissions;
import com.us.nova.feature.betacode.packets.outgoing.BetaCodeListComposer;

public class BetaCodeQueryListEvent extends MessageHandler {

    @Override
    public void handle() {
        boolean canReadBetaCodes = this.client.getHabbo().getHabboInfo().getPermissionGroup().hasPermissionRight(BetaCodePermissions.READ, false);

        if (!canReadBetaCodes) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.beta-code.read.not-allowed"));
            return;
        }

        this.client.sendResponse(new BetaCodeListComposer());
    }

}