package com.us.archangel.feature.bank.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.us.archangel.corp.model.CorpModel;
import com.us.archangel.corp.service.CorpService;
import com.us.archangel.player.model.PlayerBankAccountModel;
import com.us.archangel.player.service.PlayerBankAccountService;

public class BankAccountDepositCommand extends Command  {

    public BankAccountDepositCommand() {
        super("cmd_bank_deposit");
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
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.corp_not_found"));
            return true;
        }

        PlayerBankAccountModel bankAccount = PlayerBankAccountService.getInstance().getByUserIdAndCorpId(gameClient.getHabbo().getHabboInfo().getId(), corpID);

        if (bankAccount == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.bank.account_not_found"));
            return true;
        }

        int depositAmount = Integer.parseInt(params[2]);

        if (gameClient.getHabbo().getHabboInfo().getCredits() < depositAmount) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.not_enough_credits"));
            return true;
        }

        gameClient.getHabbo().getHabboInfo().setCredits(gameClient.getHabbo().getHabboInfo().getCredits() - depositAmount);
        bankAccount.addAccountBalance(depositAmount);
        PlayerBankAccountService.getInstance().update(bankAccount.getId(), bankAccount);

        gameClient.getHabbo().shout(Emulator.getTexts()
                .getValue("roleplay.bank.deposit_success")
                .replace(":credits", String.valueOf(depositAmount))
        );

        return true;
    }
}
