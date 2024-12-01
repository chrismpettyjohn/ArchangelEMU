package com.us.archangel.feature.combat.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.rooms.RoomTile;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.combat.packets.outgoing.CombatDelayComposer;
import com.us.archangel.feature.combat.packets.outgoing.UserDiedComposer;
import com.us.archangel.feature.hospital.actions.TeleportHospitalAction;
import com.us.archangel.feature.player.packets.outgoing.UserRoleplayStatsChangeComposer;
import com.us.archangel.player.enums.PlayerAction;
import com.us.archangel.player.model.PlayerWeaponModel;
import com.us.archangel.room.enums.RoomType;
import com.us.archangel.weapon.enums.WeaponEffect;
import com.us.archangel.weapon.enums.WeaponType;
import com.us.nova.core.NotificationHelper;

import java.util.concurrent.TimeUnit;

public class UserAttackEvent extends MessageHandler {

    @Override
    public void handle() {
        if (!canAttack()) {
            return;
        }

        int x = this.packet.readInt();
        int y = this.packet.readInt();
        PlayerWeaponModel equippedWeapon = getEquippedWeapon();

        RoomTile roomTile = getRoomTile(x, y);
        if (roomTile == null || isPassiveRoom()) {
            return;
        }

        Habbo targetedHabbo = getTargetedHabbo(x, y);
        if (targetedHabbo == null || !isInRange(x, y, equippedWeapon)) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.attack.target_missed"));
            return;
        }

        if (!hasEnoughEnergy(targetedHabbo)) {
            return;
        }

        int totalDamage = calculateDamage(equippedWeapon);
        performAttack(targetedHabbo, totalDamage, equippedWeapon);

        if (targetedHabbo.getPlayer().isDead()) {
            handleTargetDeath(targetedHabbo);
        }
    }

    private boolean canAttack() {
        if (!this.client.getHabbo().getPlayer().canInteract() || this.client.getHabbo().getPlayer().isCombatBlocked()) {
            this.client.getHabbo().whisper("You need to wait a bit before attacking again.");
            this.client.sendResponse(new CombatDelayComposer(this.client.getHabbo()));
            return false;
        }
        return true;
    }

    private PlayerWeaponModel getEquippedWeapon() {
        return this.client.getHabbo().getInventory().getWeaponsComponent().getEquippedWeapon();
    }

    private RoomTile getRoomTile(int x, int y) {
        return this.client.getHabbo().getRoomUnit().getRoom().getLayout().getTile((short) x, (short) y);
    }

    private boolean isPassiveRoom() {
        return this.client.getHabbo().getRoomUnit().getRoom().getRoomInfo().getTags().contains(RoomType.PASSIVE);
    }

    private Habbo getTargetedHabbo(int x, int y) {
        return this.client.getHabbo().getRoomUnit().getRoom().getRoomUnitManager()
                .getHabbosAt(getRoomTile(x, y))
                .stream().findFirst().orElse(null);
    }

    private boolean isInRange(int x, int y, PlayerWeaponModel equippedWeapon) {
        int rangeInTiles = equippedWeapon != null ? equippedWeapon.getWeapon().getRangeInTiles() : 1;
        int distanceX = Math.abs(x - this.client.getHabbo().getRoomUnit().getCurrentPosition().getX());
        int distanceY = Math.abs(y - this.client.getHabbo().getRoomUnit().getCurrentPosition().getY());
        return distanceX <= rangeInTiles && distanceY <= rangeInTiles;
    }

    private boolean hasEnoughEnergy(Habbo targetedHabbo) {
        int totalEnergy = Emulator.getConfig().getInt("roleplay.attack.energy", 8);
        if (totalEnergy > this.client.getHabbo().getPlayer().getEnergyNow()) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.out_of_energy")
                    .replace(":username", targetedHabbo.getHabboInfo().getUsername()));
            return false;
        }
        return true;
    }

    private int calculateDamage(PlayerWeaponModel equippedWeapon) {
        if (equippedWeapon != null && equippedWeapon.getWeapon().getEffect() == WeaponEffect.STUN) {
            return 0;
        }
        return this.client.getHabbo().getPlayerSkills().getDamageModifier(equippedWeapon != null ? equippedWeapon.getWeapon() : null);
    }

    private void performAttack(Habbo targetedHabbo, int totalDamage, PlayerWeaponModel equippedWeapon) {
        String attackMessage = equippedWeapon != null
                ? equippedWeapon.getWeapon().getAttackMessage()
                : Emulator.getTexts().getValue("commands.roleplay.cmd_hit_success");

        this.client.getHabbo().shout(attackMessage
                .replace(":username", targetedHabbo.getHabboInfo().getUsername())
                .replace(":damage", Integer.toString(totalDamage))
                .replace(":displayName", equippedWeapon != null ? equippedWeapon.getWeapon().getDisplayName() : ""));

        if (equippedWeapon != null) {
            switch (equippedWeapon.getWeapon().getEffect()) {
                case STUN:
                    targetedHabbo.getPlayer().setCurrentAction(PlayerAction.Stunned);
                    Emulator.getThreading().run(() ->
                                    targetedHabbo.getPlayer().setCurrentAction(PlayerAction.None),
                            TimeUnit.SECONDS.toMillis(10));
                    break;

                case BLEED:
                    int bleedDmgPerSec = Math.max(totalDamage / 5, 1);
                    for (int i = 1; i <= 5; i++) {
                        Emulator.getThreading().run(() ->
                                        targetedHabbo.getPlayer().depleteHealth(bleedDmgPerSec),
                                TimeUnit.SECONDS.toMillis(i));
                    }
                    break;
            }
        }

        updateStats(equippedWeapon, totalDamage);
        targetedHabbo.getPlayer().depleteHealth(totalDamage);
        this.client.getHabbo().getPlayer().depleteEnergy(Emulator.getConfig().getInt("roleplay.attack.energy", 8));

        sendRoomNotifications(targetedHabbo);
    }

    private void updateStats(PlayerWeaponModel equippedWeapon, int totalDamage) {
        if (equippedWeapon != null) {
            this.client.getHabbo().getPlayerSkills().addWeaponXp(totalDamage);
        } else {
            this.client.getHabbo().getPlayerSkills().addMeleeXp(totalDamage);
        }
    }

    private void sendRoomNotifications(Habbo targetedHabbo) {
        NotificationHelper.sendRoom(this.client.getHabbo().getRoomUnit().getRoom().getRoomInfo().getId(),
                new UserRoleplayStatsChangeComposer(targetedHabbo));
        NotificationHelper.sendRoom(this.client.getHabbo().getRoomUnit().getRoom().getRoomInfo().getId(),
                new UserRoleplayStatsChangeComposer(this.client.getHabbo()));
    }

    private void handleTargetDeath(Habbo targetedHabbo) {
        Emulator.getThreading().run(new TeleportHospitalAction(targetedHabbo));
        NotificationHelper.sendOnline(new UserDiedComposer(targetedHabbo, this.client.getHabbo()));
        NotificationHelper.announceOnline(this.client.getHabbo().getHabboInfo().getUsername() + " killed " + targetedHabbo.getHabboInfo().getUsername());
    }
}
