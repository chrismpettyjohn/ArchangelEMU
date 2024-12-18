package com.us.archangel.feature.hospital.interactions;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.items.interactions.InteractionVendingMachine;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.users.HabboInfo;
import com.us.archangel.ammo.enums.AmmoType;
import com.us.archangel.ammo.model.AmmoModel;
import com.us.archangel.ammo.service.AmmoService;
import com.us.archangel.corp.enums.CorpIndustry;
import com.us.archangel.player.service.PlayerWeaponService;
import com.us.archangel.weapon.model.WeaponModel;
import com.us.archangel.weapon.service.WeaponService;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InteractionSyringeCabinet extends InteractionVendingMachine {

    public static String SYRINGE_TOOL_NAME = "syringe";
    public static String INTERACTION_TYPE = "rp_syringe_cabinet";

    public InteractionSyringeCabinet(ResultSet set, Item baseItem) throws SQLException {
        super(set, baseItem);
    }

    public InteractionSyringeCabinet(int id, HabboInfo ownerInfo, Item item, String extradata, int limitedStack, int limitedSells) {
        super(id, ownerInfo, item, extradata, limitedStack, limitedSells);
    }

    @Override
    public void onClick(GameClient client, Room room, Object[] objects) {
        if (!client.getHabbo().getPlayer().isWorking()) {
            client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.syringe_cabinet.not_allowed"));
            return;
        }

        if (client.getHabbo().getPlayer().getCorp().getIndustry() != CorpIndustry.Hospital) {
            client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.syringe_cabinet.not_allowed"));
            return;
        }

        WeaponModel healingSyringe = WeaponService.getInstance().getByUniqueName(InteractionSyringeCabinet.SYRINGE_TOOL_NAME);

        if (healingSyringe == null) {
            throw new RuntimeException("syringe not found in archangel_weapons");
        }

        AmmoModel syringeAmmo = AmmoService.getInstance().getBySizeAndType(healingSyringe.getAmmoSize(), AmmoType.STANDARD);

        if (syringeAmmo == null) {
            throw new RuntimeException("syringe ammo not found in archangel_ammo");
        }

        PlayerWeaponService.getInstance().createWithAmmo(healingSyringe, client.getHabbo().getHabboInfo().getId(), syringeAmmo);

        client.getHabbo().shout(Emulator.getTexts().getValue("roleplay.syringe_cabinet.success"));

        super.onClick(client, room, objects);
    }

}