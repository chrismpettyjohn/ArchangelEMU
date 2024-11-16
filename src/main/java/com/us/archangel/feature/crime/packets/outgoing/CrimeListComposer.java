package com.us.archangel.feature.crime.packets.outgoing;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.archangel.police.model.PoliceCrimeModel;
import com.us.archangel.police.service.PoliceCrimeService;

import java.util.List;

public class CrimeListComposer extends MessageComposer {
    @Override
    public ServerMessage composeInternal() {
        List<PoliceCrimeModel> policeCrimeModels = PoliceCrimeService.getInstance().getAll();

        this.response.init(Outgoing.crimeListComposer);

        this.response.appendInt(policeCrimeModels.size());

        for (PoliceCrimeModel crime : policeCrimeModels) {
            this.response.appendString(String.join(";",
                    String.valueOf(crime.getId()),
                    crime.getDisplayName(),
                    crime.getDescription(),
                    String.valueOf(crime.getJailTimeSeconds())
            ));
        }

        return this.response;
    }
}
