package com.us.archangel.feature.license.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.users.Habbo;
import com.us.archangel.corp.enums.CorpIndustry;
import com.us.archangel.player.enums.PlayerAction;
import com.us.roleplay.billing.UserBill;
import com.us.roleplay.billing.items.*;
import com.us.roleplay.corp.LicenseMapper;
import com.us.roleplay.database.HabboBillRepository;
import com.us.roleplay.corp.LicenseType;
import com.us.archangel.feature.bill.packets.outgoing.InvoiceReceivedComposer;

public class LicenseOfferCommand extends Command {

    public LicenseOfferCommand() {
        super("cmd_license_offer");
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) {
        if (params == null || params.length == 0) {
            return true;
        }

        String targetedUsername = params[1];

        if (gameClient.getHabbo().getPlayer().getCurrentAction() == PlayerAction.Stunned || gameClient.getHabbo().getPlayer().getCurrentAction() == PlayerAction.Cuffed|| gameClient.getHabbo().getPlayer().isDead()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.generic.not_allowed"));
            return true;
        }

        if (targetedUsername == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.user_not_found"));
            return true;
        }

        Habbo targetedHabbo = gameClient.getHabbo().getRoomUnit().getRoom().getRoomUnitManager().getRoomHabboByUsername(targetedUsername);

        if (targetedHabbo == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.user_not_found").replace("%username%", targetedUsername));
            return true;
        }

        if (targetedHabbo.getPlayer().isDead()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.roleplay.target_dead").replace(":username", targetedUsername));
            return true;
        }

        if (params[2] == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.license_not_found"));
            return true;
        }

        LicenseType licenseType = LicenseType.fromValue(Integer.parseInt(params[2]));


        if (targetedHabbo.getInventory().getLicensesComponent().getLicenseByType(licenseType) != null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.license_already_exists"));
            return true;
        }

        CorpIndustry corpIndustry = LicenseMapper.licenseTypeToCorpTag(licenseType);

        if (gameClient.getHabbo().getPlayer().getCorp().getIndustry() != corpIndustry) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.license_sell_not_allowed"));
            return true;
        }

        UserBill statement = switch (licenseType) {
            case DRIVER ->
                    HabboBillRepository.getInstance().create(new DriverLicenseBillingItem(targetedHabbo.getHabboInfo().getId(), gameClient.getHabbo().getHabboInfo().getId()));
            case FARMING ->
                    HabboBillRepository.getInstance().create(new FarmingLicenseBillingItem(targetedHabbo.getHabboInfo().getId(), gameClient.getHabbo().getHabboInfo().getId()));
            case FISHING ->
                    HabboBillRepository.getInstance().create(new FishingLicenseBillingItem(targetedHabbo.getHabboInfo().getId(), gameClient.getHabbo().getHabboInfo().getId()));
            case MINING ->
                    HabboBillRepository.getInstance().create(new MiningLicenseBillingItem(targetedHabbo.getHabboInfo().getId(), gameClient.getHabbo().getHabboInfo().getId()));
            case LUMBERJACK ->
                    HabboBillRepository.getInstance().create(new LumberjackLicenseBillingItem(targetedHabbo.getHabboInfo().getId(), gameClient.getHabbo().getHabboInfo().getId()));
            case WEAPON ->
                    HabboBillRepository.getInstance().create(new WeaponLicenseBillingItem(targetedHabbo.getHabboInfo().getId(), gameClient.getHabbo().getHabboInfo().getId()));
        };

        if (statement == null) {;
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.error"));
            return true;
        }

        targetedHabbo.getClient().sendResponse(new InvoiceReceivedComposer(statement));
        gameClient.getHabbo().shout(Emulator.getTexts().getValue("roleplay.license_offered").replace(":license", licenseType.name()).replace(":username", targetedHabbo.getHabboInfo().getUsername()));

        return true;
    }
}
