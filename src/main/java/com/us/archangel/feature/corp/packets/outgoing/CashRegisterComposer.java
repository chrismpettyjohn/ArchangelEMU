package com.us.archangel.feature.corp.packets.outgoing;

import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CashRegisterComposer extends MessageComposer {
    private final Habbo habbo;

    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.cashRegisterComposer);
        return this.response;
    }
}
