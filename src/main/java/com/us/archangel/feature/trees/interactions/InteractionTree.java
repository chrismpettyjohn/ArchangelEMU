package com.us.archangel.feature.trees.interactions;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.items.interactions.InteractionDefault;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.users.HabboInfo;
import com.us.archangel.feature.activities.actions.TreeChoppingAction;
import com.us.roleplay.database.HabboLicenseRepository;
import com.us.roleplay.corp.LicenseType;
import com.us.roleplay.users.HabboLicense;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InteractionTree extends InteractionDefault {

    public static String INTERACTION_TYPE = "rp_tree";

    public InteractionTree(ResultSet set, Item baseItem) throws SQLException {
        super(set, baseItem);
    }

    public InteractionTree(int id, HabboInfo ownerInfo, Item item, String extradata, int limitedStack, int limitedSells) {
        super(id, ownerInfo, item, extradata, limitedStack, limitedSells);
    }

    @Override
    public void onClick(GameClient client, Room room, Object[] objects) throws Exception {
        boolean isWithinOneTile = Math.abs(this.getCurrentPosition().getX() - client.getHabbo().getRoomUnit().getCurrentPosition().getX()) <= 1 && Math.abs(this.getCurrentPosition().getY() - client.getHabbo().getRoomUnit().getCurrentPosition().getY()) <= 1;

        if (!isWithinOneTile) {
            return;
        }

        HabboLicense lumberjackLicense = HabboLicenseRepository.getInstance().getByUserAndLicense(client.getHabbo().getHabboInfo().getId(), LicenseType.LUMBERJACK);

        if (lumberjackLicense == null) {
            client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.tree_chopping.no_license"));
            return;
        }

        Emulator.getThreading().run(new TreeChoppingAction(client.getHabbo(), this, client.getHabbo().getRoomUnit().getLastRoomTile()));
    }

}