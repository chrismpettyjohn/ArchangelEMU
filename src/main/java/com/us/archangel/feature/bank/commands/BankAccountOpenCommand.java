package com.us.archangel.feature.bank.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.users.Habbo;
import com.us.archangel.corp.enums.CorpIndustry;
import com.us.archangel.corp.model.CorpModel;
import com.us.archangel.player.entity.PlayerBankAccountEntity;
import com.us.archangel.player.mapper.PlayerBankAccountMapper;
import com.us.archangel.player.model.PlayerBankAccountModel;
import com.us.archangel.player.service.PlayerBankAccountService;

public class BankAccountOpenCommand extends Command  {

    public BankAccountOpenCommand() {
        super("cmd_bank_open");
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) {
        if (params == null) {
            return true;
        }

        if (params.length != 2) {
            return true;
        }

        if (!gameClient.getHabbo().getPlayer().isWorking()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.roleplay.must_be_working"));
            return true;
        }

        CorpModel bankCorp = gameClient.getHabbo().getPlayer().getCorp();

        if (bankCorp == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.corp_not_found"));
            return true;
        }

        if (bankCorp.getIndustry() != CorpIndustry.Bank) {
            gameClient.getHabbo().whisper(Emulator.getTexts()
                    .getValue("roleplay.bank.corp_not_a_bank")
                    .replace(":corpName", bankCorp.getDisplayName())
            );
            return true;
        }

        if (gameClient.getHabbo().getRoomUnit().getRoom().getRoomInfo().getId() !=  bankCorp.getRoomId()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.generic.not_at_work"));
            return true;
        }

        String username = params[1];
        Habbo targetedUser = Emulator.getGameEnvironment().getHabboManager().getHabbo(username);

        if (targetedUser == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts()
                    .getValue("generic.user_not_found")
                    .replace(":username", username)
            );
            return true;
        }

        if (targetedUser.getRoomUnit().getRoom().getRoomInfo().getId() != gameClient.getHabbo().getRoomUnit().getRoom().getRoomInfo().getId()) {
            gameClient.getHabbo().whisper(Emulator.getTexts()
                    .getValue("roleplay.generic.user_not_in_room")
                    .replace(":username", username)
            );
            return true;
        }

        PlayerBankAccountModel bankAccount = PlayerBankAccountService.getInstance().getByUserIdAndCorpId(targetedUser.getHabboInfo().getId(), bankCorp.getId());

        if (bankAccount != null) {
            gameClient.getHabbo().whisper(Emulator.getTexts()
                    .getValue("roleplay.bank.roleplay.bank.account_already_exists")
                    .replace(":username", targetedUser.getHabboInfo().getUsername())
            );
            return true;
        }

        int currentTime = (int) (System.currentTimeMillis() / 1000);

        PlayerBankAccountEntity playerBankAccount = new PlayerBankAccountEntity();

        playerBankAccount.setCorpId(bankCorp.getId());
        playerBankAccount.setUserId(targetedUser.getHabboInfo().getId());
        playerBankAccount.setAccountBalance(0);
        playerBankAccount.setCreatedAt(currentTime);
        playerBankAccount.setUpdatedAt(currentTime);

        PlayerBankAccountService.getInstance().create(PlayerBankAccountMapper.toModel(playerBankAccount));

        gameClient.getHabbo().shout(Emulator.getTexts()
                .getValue("roleplay.bank.account_started")
                .replace(":username", targetedUser.getHabboInfo().getUsername())
                .replace(":corpName", bankCorp.getDisplayName())
        );

        gameClient.getHabbo().shout(Emulator.getTexts()
                .getValue("roleplay.bank.assisted_account_start")
                .replace(":username", targetedUser.getHabboInfo().getUsername())
        );

        return true;
    }
}
