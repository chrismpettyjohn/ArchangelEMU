package com.us.archangel.feature.license.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.users.Habbo;
import com.us.archangel.corp.model.CorpModel;
import com.us.archangel.corp.service.CorpService;
import com.us.roleplay.database.HabboLicenseRepository;
import com.us.roleplay.corp.LicenseType;
import com.us.roleplay.corp.LicenseMapper;
import com.us.archangel.feature.license.packets.outgoing.LicenseStatusComposer;
import com.us.roleplay.users.HabboLicense;

public class LicenseStatusCommand extends Command  {

    public LicenseStatusCommand() {
        super("cmd_license_lookup");
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
        CorpModel licenseCorp = CorpService.getInstance().getById(corpID);

        if (licenseCorp == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.corp_not_found"));
            return true;
        }

        LicenseType licenseType = LicenseMapper.corpIndustryToLicenseType(licenseCorp.getIndustry());

        if (licenseType == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.license.corp_not_allowed"));
            return true;
        }

        String username = params[2];
        Habbo targetedHabbo = Emulator.getGameEnvironment().getHabboManager().getHabbo(username);

        if ( targetedHabbo == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts()
                    .getValue("generic.user_not_found")
                    .replace(":username", username)
            );
            return true;
        }

        HabboLicense targetedLicense = HabboLicenseRepository.getInstance().getByUserAndLicense(targetedHabbo.getHabboInfo().getId(), licenseType);

        if (targetedLicense == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts()
                    .getValue("roleplay.license.user_missing_license")
                    .replace(":username", targetedHabbo.getHabboInfo().getUsername())
                    .replace(":license", licenseType.name())
            );
            return true;
        }

        gameClient.sendResponse(new LicenseStatusComposer(licenseType, targetedLicense));

        return true;
    }
}
