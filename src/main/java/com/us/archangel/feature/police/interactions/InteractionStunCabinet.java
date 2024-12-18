package com.us.archangel.feature.police.interactions;

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
import com.us.archangel.player.model.PlayerAmmoModel;
import com.us.archangel.player.service.PlayerAmmoService;
import com.us.archangel.player.service.PlayerWeaponService;
import com.us.archangel.weapon.model.WeaponModel;
import com.us.archangel.weapon.service.WeaponService;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InteractionStunCabinet extends InteractionVendingMachine {

    public static String STUN_GUN_NAME = "tazer";
    public static String INTERACTION_TYPE = "rp_stun_cabinet";

    public InteractionStunCabinet(ResultSet set, Item baseItem) throws SQLException {
        super(set, baseItem);
    }

    public InteractionStunCabinet(int id, HabboInfo ownerInfo, Item item, String extradata, int limitedStack, int limitedSells) {
        super(id, ownerInfo, item, extradata, limitedStack, limitedSells);
    }

    @Override
    public void onClick(GameClient client, Room room, Object[] objects) {
        if (!client.getHabbo().getPlayer().isWorking()) {
            client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.stun_cabinet.not_allowed"));
            return;
        }

        if (client.getHabbo().getPlayer().getCorp().getIndustry() != CorpIndustry.Police) {
            client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.stun_cabinet.not_allowed"));
            return;
        }

        WeaponModel stunGun = WeaponService.getInstance().getByUniqueName(InteractionStunCabinet.STUN_GUN_NAME);

        if (stunGun == null) {
            throw new RuntimeException("stun not found in archangel_weapons");
        }

        AmmoModel stunAmmo = AmmoService.getInstance().getBySizeAndType(stunGun.getAmmoSize(), AmmoType.STANDARD);

        if (stunAmmo == null) {
            throw new RuntimeException("stun ammo not found in archangel_ammo");
        }

        PlayerAmmoModel currentStunCarts = PlayerAmmoService.getInstance().getByUserAndAmmoId(client.getHabbo().getHabboInfo().getId(), stunAmmo.getId());
        int currentStunCartCount = currentStunCarts != null ? currentStunCarts.getAmmoRemaining() : 0;

        if (currentStunCartCount >= Emulator.getConfig().getInt("roleplay.stun_cabinet.max_inventory", 1)) {
            client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.stun_cabinet.max_reached"));
            return;
        }

        PlayerWeaponService.getInstance().createWithAmmo(stunGun, client.getHabbo().getHabboInfo().getId(), stunAmmo);

        client.getHabbo().shout(Emulator.getTexts().getValue("roleplay.stun_cabinet.success"));

        super.onClick(client, room, objects);
    }

}