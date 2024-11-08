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
        int x = this.packet.readInt() + 1;
        int y = this.packet.readInt();

        PlayerWeaponModel equippedWeapon = this.client.getHabbo().getInventory().getWeaponsComponent().getEquippedWeapon();

        if (equippedWeapon != null && !handleWeaponUsage(equippedWeapon)) {
            return;
        }

        RoomTile roomTile = this.client.getHabbo().getRoomUnit().getRoom().getLayout().getTile((short) x, (short) y);
        if (roomTile == null || checkCombatBlocked() || checkPassiveRoom()) return;

        Habbo targetedHabbo = this.client.getHabbo().getRoomUnit().getRoom().getRoomUnitManager().getHabbosAt(roomTile)
                .stream().findFirst().orElse(null);

        if (targetedHabbo == null || !isInRange(x, y, equippedWeapon)) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.attack.target_missed"));
            return;
        }

        if (!checkEnergyLevel(targetedHabbo)) return;

        int totalDamage = calculateDamage(this.client.getHabbo(), equippedWeapon);
        handleAttackMessages(targetedHabbo, equippedWeapon, totalDamage);
        processAttackEffects(targetedHabbo, totalDamage);
    }

    private boolean checkCombatBlocked() {
        if (this.client.getHabbo().getPlayer().isCombatBlocked()) {
            int delayRemaining = (int) this.client.getHabbo().getPlayer().getCombatDelayRemainingSecs();
            this.client.getHabbo().whisper("You need to wait a bit before attacking again.");
            this.client.sendResponse(new CombatDelayComposer(this.client.getHabbo()));

            // Scheduler to end the delay without polling packets
            ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
            executor.schedule(() -> {
                this.client.getHabbo().getPlayer().setCombatDelayRemainingSecs(0);
                this.client.sendResponse(new CombatDelayComposer(this.client.getHabbo()));
            }, delayRemaining, TimeUnit.SECONDS);
            return true;
        }
        return false;
    }

    private boolean checkPassiveRoom() {
        if (this.client.getHabbo().getRoomUnit().getRoom().getRoomInfo().getTags().contains(RoomType.PASSIVE)) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.generic.passive_room"));
            return true;
        }
        return false;
    }

    private boolean handleWeaponUsage(PlayerWeaponModel weapon) {
        if (weapon.getWeapon().getType() == WeaponType.GUN) {
            if (weapon.getAmmoRemaining() == 0) {
                this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.combat_out_of_ammo"));
                this.client.getHabbo().getPlayer().setCombatDelayRemainingSecs(weapon.getWeapon().getReloadTime());
                return false;
            }
            weapon.depleteAmmo(1);
            this.client.sendResponse(new UserRoleplayStatsChangeComposer(this.client.getHabbo()));
        }
        this.client.getHabbo().getPlayer().setCombatDelayRemainingSecs(weapon.getWeapon().getCooldownSeconds());
        return true;
    }

    private boolean isInRange(int x, int y, PlayerWeaponModel equippedWeapon) {
        int rangeInTiles = equippedWeapon != null ? equippedWeapon.getWeapon().getRangeInTiles() : 1;
        int distanceX = Math.abs(x - this.client.getHabbo().getRoomUnit().getCurrentPosition().getX());
        int distanceY = Math.abs(y - this.client.getHabbo().getRoomUnit().getCurrentPosition().getY());
        return distanceX <= rangeInTiles && distanceY <= rangeInTiles;
    }

    private boolean checkEnergyLevel(Habbo target) {
        int totalEnergy = Emulator.getConfig().getInt("roleplay.attack.energy", 8);
        if (totalEnergy > this.client.getHabbo().getPlayer().getEnergyNow()) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.out_of_energy")
                    .replace(":username", target.getHabboInfo().getUsername()));
            return false;
        }
        return true;
    }

    private int calculateDamage(Habbo player, PlayerWeaponModel equippedWeapon) {
        return player.getPlayerSkills().getDamageModifier(equippedWeapon != null ? equippedWeapon.getWeapon() : null);
    }

    private void handleAttackMessages(Habbo target, PlayerWeaponModel equippedWeapon, int damage) {
        String messageKey = equippedWeapon != null ? equippedWeapon.getWeapon().getAttackMessage()
                : Emulator.getTexts().getValue("commands.roleplay.cmd_hit_success");
        this.client.getHabbo().shout(messageKey.replace(":username", target.getHabboInfo().getUsername())
                .replace(":damage", Integer.toString(damage))
                .replace(":displayName", equippedWeapon != null ? equippedWeapon.getWeapon().getDisplayName() : ""));
        if (equippedWeapon != null) {
            this.client.getHabbo().getPlayerSkills().addWeaponXp(damage);
        } else {
            this.client.getHabbo().getPlayerSkills().addMeleeXp(damage);
        }
    }

    private void processAttackEffects(Habbo target, int damage) {
        target.getPlayer().depleteHealth(damage);
        this.client.getHabbo().getPlayer().depleteEnergy(Emulator.getConfig().getInt("roleplay.attack.energy", 8));

        if (target.getPlayer().isDead()) {
            Collection<Habbo> onlineHabbos = Emulator.getGameEnvironment().getHabboManager().getOnlineHabbos().values();
            onlineHabbos.forEach(onlineHabbo -> onlineHabbo.getClient().sendResponse(new UserDiedComposer(target, this.client.getHabbo())));
        }
    }
}
