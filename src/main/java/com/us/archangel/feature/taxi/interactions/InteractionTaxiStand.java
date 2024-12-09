package com.us.archangel.feature.taxi.interactions;

import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.items.interactions.InteractionDefault;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.users.HabboInfo;
import com.us.archangel.feature.taxi.packets.outgoing.TaxiStandComposer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InteractionTaxiStand extends InteractionDefault {

    public static final String INTERACTION_TYPE = "rp_taxi_stand";

    public InteractionTaxiStand(ResultSet set, Item baseItem) throws SQLException {
        super(set, baseItem);
    }

    public InteractionTaxiStand(int id, HabboInfo ownerInfo, Item item, String extradata, int limitedStack, int limitedSells) {
        super(id, ownerInfo, item, extradata, limitedStack, limitedSells);
    }

    @Override
    public void onClick(GameClient client, Room room, Object[] objects) throws Exception {
        super.onClick(client, room, objects);
        client.sendResponse(new TaxiStandComposer());
    }
}