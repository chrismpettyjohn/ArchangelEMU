package com.us.archangel.feature.gunstore.interactions;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.items.interactions.InteractionDefault;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.users.HabboInfo;
import com.us.archangel.corp.enums.CorpIndustry;
import com.us.archangel.corp.model.CorpModel;
import com.us.archangel.feature.gunstore.packets.outgoing.AmmoCrateDataComposer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InteractionAmmoCrate extends InteractionDefault {
    public static String INTERACTION_TYPE = "rp_ammo_crate";

    public InteractionAmmoCrate(ResultSet set, Item baseItem) throws SQLException {
        super(set, baseItem);
    }

    public InteractionAmmoCrate(int id, HabboInfo ownerInfo, Item item, String extradata, int limitedStack, int limitedSells) {
        super(id, ownerInfo, item, extradata, limitedStack, limitedSells);
    }

    @Override
    public void onClick(final GameClient client, final Room room, Object[] objects) throws Exception {
        if (!client.getHabbo().getPlayer().isWorking()) {
            client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.ammo_crate.not_allowed"));
            return;
        }

        CorpModel userCorp = client.getHabbo().getPlayer().getCorp();

        if (userCorp.getIndustry() != CorpIndustry.GunStore) {
            client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.ammo_crate.not_allowed"));
            return;
        }

        client.sendResponse(new AmmoCrateDataComposer());
    }
}