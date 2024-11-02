package com.us.nova.feature.betacode.packets.outgoing;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.nova.betacode.model.BetaCodeModel;
import com.us.nova.betacode.service.BetaCodeService;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
public class BetaCodeListComposer extends MessageComposer {
    @Override
    protected ServerMessage composeInternal() {
        List<BetaCodeModel> betaCodeModels = BetaCodeService.getInstance().getAll();

        this.response.init(Outgoing.betaCodeListComposer);
        this.response.appendInt(betaCodeModels.size());

        for (BetaCodeModel betaCode : betaCodeModels) {
            Habbo claimedByHabbo = betaCode.getClaimedByUserId() != null
                ? Emulator.getGameEnvironment().getHabboManager().getHabbo(betaCode.getClaimedByUserId())
                : null;
            this.response.appendString(betaCode.getId() + ";" + betaCode.getCode() + ";" + betaCode.getClaimedByUserId() + ";" + (claimedByHabbo != null ? claimedByHabbo.getHabboInfo().getUsername() : "") + ";" + (betaCode.getClaimedAt() != null ? betaCode.getClaimedAt() : -1) + ";" + betaCode.getCreatedAt());
        }

        return this.response;
    }
}
