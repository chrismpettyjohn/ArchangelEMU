package com.us.archangel.feature.road.interactions;

import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.items.interactions.InteractionDefault;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.entities.units.RoomUnit;
import com.eu.habbo.habbohotel.users.HabboInfo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InteractionRoad extends InteractionDefault {

    public static String INTERACTION_TYPE = "rp_road";

    public InteractionRoad(ResultSet set, Item baseItem) throws SQLException {
        super(set, baseItem);
    }

    public InteractionRoad(int id, HabboInfo ownerInfo, Item item, String extradata, int limitedStack, int limitedSells) {
        super(id, ownerInfo, item, extradata, limitedStack, limitedSells);
    }


    @Override
    public void onWalkOn(RoomUnit roomUnit, Room room, Object[] objects) throws Exception {
        super.onWalkOn(roomUnit, room, objects);
        if (roomUnit.isCmdFastWalkEnabled() && roomUnit.isTemporalFastWalkEnabled()) {
            return;
        }
        roomUnit.setTemporalFastWalkEnabled(true);
        roomUnit.setCmdFastWalkEnabled(true);
    }

    @Override
    public void onWalkOff(RoomUnit roomUnit, Room room, Object[] objects) throws Exception {
        super.onWalkOff(roomUnit, room, objects);
        if (!roomUnit.isCmdFastWalkEnabled() && !roomUnit.isTemporalFastWalkEnabled()) {
            return;
        }
        roomUnit.setTemporalFastWalkEnabled(false);
        roomUnit.setCmdFastWalkEnabled(false);
    }
}