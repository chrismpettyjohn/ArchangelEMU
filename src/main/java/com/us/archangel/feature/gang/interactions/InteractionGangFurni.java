package com.us.archangel.feature.gang.interactions;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.items.interactions.InteractionGuildFurni;
import com.eu.habbo.habbohotel.users.HabboInfo;
import com.eu.habbo.messages.ServerMessage;
import com.us.archangel.gang.model.GangModel;
import com.us.archangel.gang.service.GangService;
import lombok.Getter;
import lombok.Setter;

import java.sql.ResultSet;
import java.sql.SQLException;

@Setter
@Getter
public class InteractionGangFurni extends InteractionGuildFurni {

    public static final String INTERACTION_TYPE =  "rp_gang";

    private int gangId;

    public InteractionGangFurni(ResultSet set, Item baseItem) throws SQLException {
        super(set, baseItem);
        this.gangId = set.getInt("gang_id");
    }

    public InteractionGangFurni(int id, HabboInfo ownerInfo, Item item, String extradata, int limitedStack, int limitedSells) {
        super(id, ownerInfo, item, extradata, limitedStack, limitedSells);
        this.gangId = 0;
    }

    @Override
    public void serializeExtradata(ServerMessage serverMessage) {
        GangModel gang = GangService.getInstance().getById(this.gangId);

        if (gang != null) {
            serverMessage.appendInt(2 + (this.isLimited() ? 256 : 0));
            serverMessage.appendInt(5);
            serverMessage.appendString(this.getExtraData());
            serverMessage.appendString(gang.getId() + "");
            serverMessage.appendString(gang.getBadge());
            serverMessage.appendString(Emulator.getGameEnvironment().getGuildManager().getSymbolColor(gang.getColorPrimary()).getValueA());
            serverMessage.appendString(Emulator.getGameEnvironment().getGuildManager().getBackgroundColor(gang.getColorSecondary()).getValueA());
        } else {
            serverMessage.appendInt((this.isLimited() ? 256 : 0));
            serverMessage.appendString(this.getExtraData());
        }

        if (this.isLimited()) {
            serverMessage.appendInt(this.getLimitedSells());
            serverMessage.appendInt(this.getLimitedStack());
        }
    }

}
