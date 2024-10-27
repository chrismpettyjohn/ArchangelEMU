package com.us.archangel.feature.combat.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.us.archangel.feature.player.packets.outgoing.UserRoleplayStatsChangeComposer;
import com.us.archangel.player.model.PlayerWeaponModel;
import com.us.archangel.weapon.context.WeaponContext;

public class EquipCommand extends Command {
    public EquipCommand() {
        super("cmd_equip");
    }
    @Override
    public boolean handle(GameClient gameClient, String[] params) {
        if (params.length < 2) {
            PlayerWeaponModel prevEquippedWeapon = gameClient.getHabbo().getInventory().getWeaponsComponent().getEquippedWeapon();
            gameClient.getHabbo().getInventory().getWeaponsComponent().setEquippedWeapon(null);
            gameClient.getHabbo().getRoomUnit().giveEffect(0, -1);
            gameClient.getHabbo().getRoomUnit().getRoom().sendComposer(new UserRoleplayStatsChangeComposer(gameClient.getHabbo()).compose());
            gameClient.getHabbo().shout(prevEquippedWeapon.getWeapon().getUnequipMessage().replace(":displayName", prevEquippedWeapon.getWeapon().getDisplayName()));
            return true;
        }

        String weaponUniqueName = params[1];

        if (weaponUniqueName == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_equip_no_weapon_specified"));
            return true;
        }

        PlayerWeaponModel matchingWeapon = gameClient.getHabbo().getInventory().getWeaponsComponent().getWeaponByUniqueName(weaponUniqueName);
        if (matchingWeapon == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_equip_weapon_not_found").replace(":uniqueName", weaponUniqueName));
            return true;
        }

        gameClient.getHabbo().getInventory().getWeaponsComponent().setEquippedWeapon(matchingWeapon);
        gameClient.getHabbo().getRoomUnit().giveEffect(WeaponContext.getInstance().get(matchingWeapon.getId()).getEquipEffect(), -1);
        gameClient.getHabbo().getRoomUnit().getRoom().sendComposer(new UserRoleplayStatsChangeComposer(gameClient.getHabbo()).compose());
        gameClient.getHabbo().shout(matchingWeapon.getWeapon().getEquipMessage().replace(":displayName", matchingWeapon.getWeapon().getDisplayName()));

        return true;
    }
}
