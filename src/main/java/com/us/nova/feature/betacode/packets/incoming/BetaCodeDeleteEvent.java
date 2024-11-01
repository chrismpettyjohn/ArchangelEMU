package com.us.nova.feature.betacode.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.nova.betacode.service.BetaCodeService;
import com.us.nova.feature.betacode.packets.outgoing.BetaCodeListComposer;

public class BetaCodeDeleteEvent extends MessageHandler {
    static final String BETA_CODE_DELETE_PERMISSION = "beta-code-delete";

    @Override
    public void handle() {
        boolean canDeleteBetaCode = this.client.getHabbo().getHabboInfo().getPermissionGroup().hasPermissionRight(BETA_CODE_DELETE_PERMISSION, false);

        if (!canDeleteBetaCode) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.beta-code.delete.not-allowed"));
            return;
        }

        int betaCodeId = this.packet.readInt();
        BetaCodeService.getInstance().deleteById(betaCodeId);
        this.client.sendResponse(new BetaCodeListComposer());
    }
}
