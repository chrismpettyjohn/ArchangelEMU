package com.us.archangel.feature.fish.interactions;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.items.interactions.InteractionWater;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.entities.units.RoomUnit;
import com.eu.habbo.habbohotel.users.HabboInfo;
import com.us.archangel.feature.fish.actions.FishingAction;
import com.us.roleplay.database.HabboLicenseRepository;
import com.us.roleplay.corp.LicenseType;
import com.us.roleplay.users.HabboLicense;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InteractionFishingSource extends InteractionWater {

    public static String INTERACTION_TYPE = "rp_fishing_source";

    public InteractionFishingSource(ResultSet set, Item baseItem) throws SQLException {
        super(set, baseItem);
    }

    public InteractionFishingSource(int id, HabboInfo ownerInfo, Item item, String extradata, int limitedStack, int limitedSells) {
        super(id, ownerInfo, item, extradata, limitedStack, limitedSells);
    }

    @Override
    public void onClick(GameClient client, Room room, Object[] objects) throws Exception {
        boolean isWithinOneTile = Math.abs(this.getCurrentPosition().getX() - client.getHabbo().getRoomUnit().getCurrentPosition().getX()) <= 1 && Math.abs(this.getCurrentPosition().getY() - client.getHabbo().getRoomUnit().getCurrentPosition().getY()) <= 1;

        if (!isWithinOneTile) {
            return;
        }

        HabboLicense miningLicense = HabboLicenseRepository.getInstance().getByUserAndLicense(client.getHabbo().getHabboInfo().getId(), LicenseType.FISHING);

        if (miningLicense == null) {
            client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.fishing.no_license"));
            return;
        }
        Emulator.getThreading().run(new FishingAction(client.getHabbo(), this, client.getHabbo().getRoomUnit().getLastRoomTile()));
    }

    @Override
    public boolean canWalkOn(RoomUnit roomUnit, Room room, Object[] objects) {
        return false;
    }

    @Override
    public boolean isWalkable() {
        return false;
    }
}