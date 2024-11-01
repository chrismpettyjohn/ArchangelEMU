package com.us.nova.feature.betacode.packets.outgoing;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.nova.betacode.model.BetaCodeModel;
import com.us.nova.betacode.service.BetaCodeService;

import java.util.List;

public class BetaCodeListComposer extends MessageComposer {
    @Override
    protected ServerMessage composeInternal() {
        List<BetaCodeModel> betaCodes = BetaCodeService.getInstance().getAll();
        this.response.init(Outgoing.betaCodeListComposer);
        this.response.appendInt(betaCodes.size());

        for (BetaCodeModel betaCode : betaCodes) {
            this.response.appendString(betaCode.getId() + ";" + betaCode.getCode() + ";" + betaCode.getClaimedByUserId() + ";" + betaCode.getClaimedAt());
        }

        return this.response;
    }
}
