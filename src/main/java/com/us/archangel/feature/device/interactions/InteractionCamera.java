package com.us.archangel.feature.device.interactions;

import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.users.HabboInfo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InteractionCamera extends InteractionUsable {

    public static int CAMERA_EFFECT_ID = 65;

    public static String INTERACTION_TYPE = "rp_camera";

    public InteractionCamera(ResultSet set, Item baseItem) throws SQLException {
        super(set, baseItem);
    }

    public InteractionCamera(int id, HabboInfo ownerInfo, Item item, String extradata, int limitedStack, int limitedSells) {
        super(id, ownerInfo, item, extradata, limitedStack, limitedSells);
    }

}