package com.us.archangel.feature.bank.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.users.Habbo;
import com.us.archangel.corp.model.CorpModel;
import com.us.archangel.corp.service.CorpService;
import com.us.archangel.feature.bank.packets.outgoing.BankAccountInfoComposer;
import com.us.archangel.player.model.PlayerBankAccountModel;
import com.us.archangel.player.service.PlayerBankAccountService;

public class BankAccountLookupCommand extends Command  {

    public BankAccountLookupCommand() {
        super("cmd_bank_lookup");
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

        String username = params[2];
        Habbo bankMember = Emulator.getGameEnvironment().getHabboManager().getHabbo(username);

        if ( bankMember == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts()
                    .getValue("generic.user_not_found")
                    .replace(":username", username)
            );
            return true;
        }

        PlayerBankAccountModel bankAccount = PlayerBankAccountService.getInstance().getByUserIdAndCorpId(bankMember.getHabboInfo().getId(), corpID);

        if (bankAccount == null) {
            return true;
        }

       gameClient.sendResponse(new BankAccountInfoComposer(bankAccount));

        return true;
    }
}
