package com.us.archangel.feature.corp.packets.outgoing;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.archangel.corp.model.CorpModel;
import com.us.archangel.corp.service.CorpService;
import lombok.AllArgsConstructor;
import java.util.List;

@AllArgsConstructor
public class CorpListComposer extends MessageComposer {

    @Override
    protected ServerMessage composeInternal() {
        List<CorpModel> corps = CorpService.getInstance().getAll();

        this.response.init(Outgoing.corpListComposer);
        this.response.appendInt(corps.size());

        // Append the sorted positions to the response
        for (CorpModel corp : corps) {
            this.response.appendString(
                    corp.getId()
                            + ";" + corp.getDisplayName()
            );
        }

        return this.response;
    }
}
