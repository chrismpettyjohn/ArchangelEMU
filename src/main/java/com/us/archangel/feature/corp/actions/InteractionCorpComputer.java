package com.us.archangel.feature.corp.actions;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.items.interactions.InteractionDefault;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.users.HabboInfo;
import com.us.archangel.corp.model.CorpModel;
import com.us.archangel.feature.corp.packets.outgoing.CorpOpenComputerComposer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InteractionCorpComputer extends InteractionDefault {

    public static String INTERACTION_TYPE = "rp_corp_pc";

    public InteractionCorpComputer(ResultSet set, Item baseItem) throws SQLException {
        super(set, baseItem);
    }

    public InteractionCorpComputer(int id, HabboInfo ownerInfo, Item item, String extradata, int limitedStack, int limitedSells) {
        super(id, ownerInfo, item, extradata, limitedStack, limitedSells);
    }

    @Override
    public void onClick(GameClient client, Room room, Object[] objects) throws Exception {
        CorpModel corp = client.getHabbo().getPlayer().getCorp();

        if (corp.getUserId() != client.getHabbo().getHabboInfo().getId()) {
            client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.cor.not_the_owner"));
            return;
        }

        if (!client.getHabbo().getPlayer().isWorking()) {
            client.getHabbo().whisper(Emulator.getTexts().getValue("generic.roleplay.must_be_working"));
            return;
        }

        client.getHabbo().shout(Emulator.getTexts()
                .getValue("roleplay.computer.logged_in")
                .replace(":corpName", corp.getDisplayName())
        );

        client.sendResponse(new CorpOpenComputerComposer(this.getId(), corp.getId()));
    }
}