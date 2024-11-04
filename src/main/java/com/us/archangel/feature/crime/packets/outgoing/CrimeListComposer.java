package com.us.archangel.feature.crime.packets.outgoing;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.archangel.crime.model.CrimeModel;
import com.us.archangel.crime.service.CrimeService;

import java.util.List;

public class CrimeListComposer extends MessageComposer {
    @Override
    public ServerMessage composeInternal() {
        List<CrimeModel> crimeModels = CrimeService.getInstance().getAll();

        this.response.init(Outgoing.crimeListComposer);

        this.response.appendInt(crimeModels.size());

        for (CrimeModel crime : crimeModels) {
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
