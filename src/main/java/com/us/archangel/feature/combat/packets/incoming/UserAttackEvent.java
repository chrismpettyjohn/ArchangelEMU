package com.us.archangel.feature.combat.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.rooms.RoomTile;
import com.eu.habbo.habbohotel.users.Habbo;
import com.us.archangel.feature.combat.packets.outgoing.CombatDelayComposer;
import com.us.archangel.feature.combat.packets.outgoing.WeaponActionComposer;
import com.us.archangel.feature.combat.packets.outgoing.UserDiedComposer;
import com.us.archangel.feature.player.packets.outgoing.UserRoleplayStatsChangeComposer;
import com.us.archangel.player.model.PlayerWeaponModel;
import com.us.archangel.room.enums.RoomType;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.weapon.enums.WeaponType;

import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UserAttackEvent extends MessageHandler {

    @Override
    public void handle() {
        int x = this.packet.readInt();
        int y = this.packet.readInt();
        int z = this.packet.readInt();

        RoomTile roomTile = this.client.getHabbo().getRoomUnit().getRoom().getLayout().getTile((short) x, (short) y);

        if (roomTile == null) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_hit_user_is_out_of_range").replace(":username", "target"));
            return;
        }

        if (this.client.getHabbo().getPlayer().isCombatBlocked()) {
            this.client.getHabbo().whisper("You need to wait a bit before attacking again.");
            ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

            Runnable task = () -> {
                this.client.sendResponse(new CombatDelayComposer(this.client.getHabbo()));
                if (!this.client.getHabbo().getPlayer().isCombatBlocked()) {
                    executor.shutdown();
                }
            };
            executor.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
        }


        if (this.client.getHabbo().getRoomUnit().getRoom().getRoomInfo().getTags().contains(RoomType.PASSIVE)) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.generic.passive_room"));
            return;
        }

        int distanceX = x - this.client.getHabbo().getRoomUnit().getCurrentPosition().getX();
        int distanceY = y- this.client.getHabbo().getRoomUnit().getCurrentPosition().getY();

        PlayerWeaponModel equippedWeapon = this.client.getHabbo().getInventory().getWeaponsComponent().getEquippedWeapon();


        if (equippedWeapon != null && equippedWeapon.getWeapon().getType() == WeaponType.GUN) {
            if (equippedWeapon.getAmmoRemaining() == 0) {
                this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.combat_out_of_ammo"));
                this.client.getHabbo().getPlayer().setCombatDelayRemainingSecs(equippedWeapon.getWeapon().getReloadTime());
                return;
            }
            equippedWeapon.depleteAmmo(1);
            this.client.sendResponse(new UserRoleplayStatsChangeComposer(this.client.getHabbo()));
        }

        int rangeInTiles = equippedWeapon != null ? equippedWeapon.getWeapon().getRangeInTiles() : 1;

        boolean isTargetWithinRange = Math.abs(distanceX) <= rangeInTiles && Math.abs(distanceY) <= rangeInTiles;

        int combatTimeout = equippedWeapon != null ? equippedWeapon.getWeapon().getCooldownSeconds() : Emulator.getConfig().getInt("roleplay.attack.delay_secs", 1);

        this.client.getHabbo().getPlayer().setCombatDelayRemainingSecs(combatTimeout);

        Collection<Habbo> usersAtPosition = this.client.getHabbo().getRoomUnit().getRoom().getRoomUnitManager().getHabbosAt(roomTile);
        Habbo targetedHabbo = usersAtPosition.stream().findFirst().orElse(null);


        Collection<Habbo> nearbyHabbos = this.client.getHabbo().getRoomUnit().getRoom().getRoomUnitManager().getCurrentHabbos().values();
        for (Habbo nearbyHabbo : nearbyHabbos) {
            nearbyHabbo.getClient().sendResponse(new WeaponActionComposer(equippedWeapon, this.client.getHabbo()));
        }


        if (targetedHabbo == null) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.attack.target_missed"));
            return;
        }
        if (!isTargetWithinRange) {
            this.client.getHabbo().whisper(Emulator.getTexts()
                    .getValue("commands.roleplay.cmd_hit_user_is_out_of_range")
                    .replace(":username", targetedHabbo.getHabboInfo().getUsername())
            );
            return;
        }

        int totalEnergy = Emulator.getConfig().getInt("roleplay.attack.energy", 8);

        if (totalEnergy > this.client.getHabbo().getPlayer().getEnergyNow()) {
            this.client.getHabbo().whisper(Emulator.getTexts()
                    .getValue("commands.roleplay.out_of_energy")
                    .replace(":username", targetedHabbo.getHabboInfo().getUsername())
            );
            return;
        }

        int totalDamage = targetedHabbo.getPlayerSkills().getDamageModifier(equippedWeapon.getWeapon());

        if (equippedWeapon != null) {
            String hitSuccessMessage = equippedWeapon.getWeapon().getAttackMessage()
                    .replace(":username", targetedHabbo.getHabboInfo().getUsername())
                    .replace(":damage", Integer.toString(totalDamage))
                    .replace(":displayName", equippedWeapon.getWeapon().getDisplayName());
            this.client.getHabbo().shout(hitSuccessMessage);
            this.client.getHabbo().getPlayerSkills().addWeaponXp(totalDamage);
        } else {
            String hitSuccessMessage = Emulator.getTexts()
                    .getValue("commands.roleplay.cmd_hit_success")
                    .replace(":username", targetedHabbo.getHabboInfo().getUsername())
                    .replace(":damage", Integer.toString(totalDamage));
            this.client.getHabbo().shout(hitSuccessMessage);
            this.client.getHabbo().getPlayerSkills().addMeleeXp(totalDamage);
        }

        targetedHabbo.getPlayer().depleteHealth(totalDamage);

        targetedHabbo.shout(Emulator.getTexts()
                .getValue("commands.roleplay.user_health_remaining")
                .replace(":currentHealth", Integer.toString(targetedHabbo.getPlayer().getHealthNow()))
                .replace(":maximumHealth", Integer.toString(targetedHabbo.getPlayer().getHealthMax()))
        );

        this.client.getHabbo().getPlayer().depleteEnergy(totalEnergy);
        targetedHabbo.shout(Emulator.getTexts()
                .getValue("commands.roleplay.user_energy_remaining")
                .replace(":energyNow", Integer.toString(this.client.getHabbo().getPlayer().getEnergyNow()))
                .replace(":energyMax", Integer.toString(this.client.getHabbo().getPlayer().getEnergyMax()))
        );

        if (targetedHabbo.getPlayer().isDead()) {
            Collection<Habbo> onlineHabbos = Emulator.getGameEnvironment().getHabboManager().getOnlineHabbos().values();

            for (Habbo onlineHabbo : onlineHabbos) {
                onlineHabbo.getClient().sendResponse(new UserDiedComposer(targetedHabbo, this.client.getHabbo()));
            }
        }
    }
}
