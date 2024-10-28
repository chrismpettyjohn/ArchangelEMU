package com.us.archangel.feature.bank.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.us.archangel.corp.model.CorpModel;
import com.us.archangel.corp.service.CorpService;
import com.us.archangel.player.mapper.PlayerBankAccountMapper;
import com.us.archangel.player.model.PlayerBankAccountModel;
import com.us.archangel.player.service.PlayerBankAccountService;

public class BankAccountWithdrawCommand extends Command  {

    public BankAccountWithdrawCommand() {
        super("cmd_bank_withdraw");
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) {
        if (params == null) {
            return true;
        }

        if (params.length != 3) {
            return true;
        }

        int corpID = Integer.parseInt(params[1]);
        CorpModel bankCorp = CorpService.getInstance().getById(corpID);

        if (bankCorp == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.bank.corp_not_found"));
            return true;
        }

        PlayerBankAccountModel bankAccount = PlayerBankAccountService.getInstance().getByUserIdAndCorpId(gameClient.getHabbo().getHabboInfo().getId(), corpID);

        if (bankAccount == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.bank.account_not_found"));
            return true;
        }

        int withdrawAmount =Integer.parseInt(params[2]);

        if (bankAccount.getAccountBalance() < withdrawAmount) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.not_enough_credits"));
            return true;
        }

        bankAccount.deductAccountBalance(withdrawAmount);

        PlayerBankAccountService.getInstance().update(bankAccount.getId(), PlayerBankAccountMapper.toEntity(bankAccount));

        gameClient.getHabbo().shout(Emulator.getTexts()
                .getValue("roleplay.bank.withdraw_success")
                .replace(":credits", String.valueOf(withdrawAmount))
        );

        return true;
    }
}
