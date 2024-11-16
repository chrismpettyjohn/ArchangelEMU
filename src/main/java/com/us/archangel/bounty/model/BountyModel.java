package com.us.archangel.bounty.model;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.us.archangel.police.model.PoliceCrimeModel;
import com.us.archangel.police.service.PoliceCrimeService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BountyModel {

    private int id;
    private int userId;
    private int crimeId;
    private Integer closedAt;

    public BountyModel() {
    }

    public Habbo getHabbo() {
        return Emulator.getGameEnvironment().getHabboManager().getHabbo(this.userId);
    }

    public PoliceCrimeModel getCrime() {
        return PoliceCrimeService.getInstance().getById(this.crimeId);
    }

}