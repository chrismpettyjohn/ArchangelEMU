package com.us.archangel.feature.bank.packets.outgoing;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.archangel.corp.CorpManager;
import com.us.archangel.corp.enums.CorpIndustry;
import com.us.archangel.corp.model.CorpModel;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class BankListComposer extends MessageComposer {
    @Override
    protected ServerMessage composeInternal() {
        List<CorpModel> bankCorps = CorpManager.getInstance().getCorpService().findManyByIndustry(CorpIndustry.Bank);
        this.response.init(Outgoing.bankListComposer);
        this.response.appendInt(bankCorps.size());
        for (CorpModel bankCorp : bankCorps) {
            this.response.appendString(bankCorp.getId() + ";" + bankCorp.getDisplayName());
        }
        return this.response;
    }
}
