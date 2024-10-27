package com.us.archangel.feature.bank.interactions;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.items.interactions.InteractionDefault;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.users.HabboInfo;
import com.us.archangel.corp.enums.CorpIndustry;
import com.us.archangel.corp.model.CorpModel;
import com.us.archangel.corp.service.CorpService;
import com.us.archangel.feature.bank.packets.outgoing.BankOpenComputerComposer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InteractionBankComputer extends InteractionDefault {

    public static String INTERACTION_TYPE = "rp_bank_pc";

    public InteractionBankComputer(ResultSet set, Item baseItem) throws SQLException {
        super(set, baseItem);
    }

    public InteractionBankComputer(int id, HabboInfo ownerInfo, Item item, String extradata, int limitedStack, int limitedSells) {
        super(id, ownerInfo, item, extradata, limitedStack, limitedSells);
    }

    @Override
    public void onClick(GameClient client, Room room, Object[] objects) throws Exception {
        int corpID = Integer.parseInt(this.getExtraData());
        CorpModel bankCorp = CorpService.getInstance().getById(corpID);

        if (bankCorp == null) {
            if (!client.getHabbo().getPlayer().isWorking()) {
                client.getHabbo().whisper(Emulator.getTexts().getValue("generic.roleplay.must_be_working"));
                return;
            }

            if (this.getOwnerInfo().getId() != client.getHabbo().getHabboInfo().getId()) {
                client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.device.not_setup"));
                return;
            }

            client.getHabbo().shout(Emulator.getTexts().getValue("roleplay.device.set_up"));
            client.sendResponse(new BankOpenComputerComposer(this.getId(), corpID));
            return;
        }

        if (bankCorp.getIndustry() != CorpIndustry.Bank) {
            client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.bank.corp_not_a_bank"));
            return;
        }

        client.getHabbo().shout(Emulator.getTexts()
                .getValue("roleplay.computer.logged_in")
                .replace(":corpName", bankCorp.getDisplayName())
        );

        client.sendResponse(new BankOpenComputerComposer(this.getId(), corpID));
    }
}