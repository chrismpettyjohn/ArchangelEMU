package com.us.archangel.feature.combat.packets.outgoing;

import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.archangel.player.model.PlayerKillHistoryModel;
import com.us.archangel.player.service.PlayerKillHistoryService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class UserDiedComposer extends MessageComposer {
    private final Habbo victim;
    private final Habbo attacker;

    @Override
    protected ServerMessage composeInternal() {
        List<PlayerKillHistoryModel> killsBetweenPlayers = PlayerKillHistoryService.getInstance().getKillsBetweenTwoPeople(this.victim.getHabboInfo().getId(), this.attacker.getHabboInfo().getId());
        long victimKills = killsBetweenPlayers.stream().filter(kill -> kill.getAttackerUserId() == this.victim.getHabboInfo().getId()).count();
        long attackerKills = killsBetweenPlayers.stream().filter(kill -> kill.getAttackerUserId() == this.attacker.getHabboInfo().getId()).count();

        this.response.init(Outgoing.userDiedComposer);
        this.response.appendInt(this.victim.getHabboInfo().getId());
        this.response.appendString(this.victim.getHabboInfo().getUsername());
        this.response.appendString(this.victim.getHabboInfo().getLook());
        this.response.appendInt((int) victimKills);
        this.response.appendInt(this.attacker.getHabboInfo().getId());
        this.response.appendString(this.attacker.getHabboInfo().getUsername());
        this.response.appendString(this.attacker.getHabboInfo().getLook());
        this.response.appendInt((int) attackerKills);
        return this.response;
    }
}
