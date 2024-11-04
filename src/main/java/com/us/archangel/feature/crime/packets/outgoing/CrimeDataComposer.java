package com.us.archangel.feature.crime.packets.outgoing;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.archangel.crime.model.CrimeModel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CrimeDataComposer extends MessageComposer {
    private final CrimeModel crime;

    @Override
    public ServerMessage composeInternal() {
        this.response.init(Outgoing.crimeDataComposer);

        this.response.appendInt(this.crime.getId());
        this.response.appendString(this.crime.getDisplayName());
        this.response.appendString(this.crime.getDescription());
        this.response.appendInt(this.crime.getJailTimeSeconds());

        return this.response;
    }

}
