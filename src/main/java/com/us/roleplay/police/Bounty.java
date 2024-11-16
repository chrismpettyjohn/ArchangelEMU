package com.us.roleplay.police;

import com.eu.habbo.habbohotel.users.Habbo;
import com.us.archangel.crime.model.CrimeModel;
import com.us.archangel.crime.service.CrimeService;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Bounty {
    public final Habbo habbo;
    public final int crimeID;

    public CrimeModel getCrime() {
        return CrimeService.getInstance().getById(this.crimeID);
    }
}
