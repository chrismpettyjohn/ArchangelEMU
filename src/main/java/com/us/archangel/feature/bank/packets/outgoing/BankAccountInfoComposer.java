package com.us.archangel.feature.bank.packets.outgoing;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.archangel.player.model.PlayerBankAccountModel;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BankAccountInfoComposer extends MessageComposer {
    private final PlayerBankAccountModel habboBankAccount;

    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.bankAccountInfoComposer);
        this.response.appendInt(this.habboBankAccount.getCorpId());
        this.response.appendInt(this.habboBankAccount.getUserId());
        this.response.appendInt(this.habboBankAccount.getAccountBalance());
        this.response.appendInt(this.habboBankAccount.getCreatedAt());
        this.response.appendInt(this.habboBankAccount.getUpdatedAt());
        return this.response;
    }
}
