package com.us.archangel.feature.taxi.packets.outgoing;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TaxiStandComposer extends MessageComposer {
    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.taxiStandComposer);
        this.response.appendInt(Integer.parseInt(Emulator.getConfig().getValue("roleplay.taxi.fee")));
        this.response.appendInt(Integer.parseInt(Emulator.getConfig().getValue("roleplay.taxi.delay_secs")));
        return this.response;
    }
}
