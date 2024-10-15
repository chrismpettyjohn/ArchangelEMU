package com.us.archangel.feature.bank.packets.outgoing;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.roleplay.corp.Corp;
import com.us.roleplay.corp.CorpManager;
import com.us.roleplay.corp.CorpTag;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class BankListComposer extends MessageComposer {
    @Override
    protected ServerMessage composeInternal() {
        List<Corp> bankCorps = CorpManager.getInstance().getCorpsByTag(CorpTag.BANK);
        this.response.init(Outgoing.bankListComposer);
        this.response.appendInt(bankCorps.size());
        for (Corp bankCorp : bankCorps) {
            this.response.appendString(bankCorp.getGuild().getId() + ";" + bankCorp.getGuild().getName());
        }
        return this.response;
    }
}
