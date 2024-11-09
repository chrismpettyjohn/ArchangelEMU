package com.us.archangel.feature.combat.packets.outgoing;

import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CombatDelayComposer extends MessageComposer {
    private final Habbo habbo;

    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.combatDelayComposer);
        this.response.appendBoolean(this.habbo.getPlayer().isCombatBlocked());
        this.response.appendInt((int) this.habbo.getPlayer().getCombatDelayExpiresAt());
        return this.response;
    }
}
