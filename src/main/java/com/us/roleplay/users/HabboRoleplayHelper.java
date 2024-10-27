package com.us.roleplay.users;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.us.archangel.corp.enums.CorpIndustry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class HabboRoleplayHelper {

    public static List<Habbo> getUsersByCorpIndustry(CorpIndustry corpIndustry) {
        List<Habbo> habbosInCorp = new ArrayList<>();
        ConcurrentHashMap<Integer, Habbo> habbosOnline = Emulator.getGameEnvironment().getHabboManager().getOnlineHabbos();

        for (Habbo habbo : habbosOnline.values()) {
            if (habbo.getPlayer().getCorp().getIndustry() == corpIndustry) {
                habbosInCorp.add(habbo);
            }
        }

        return habbosInCorp;
    }

    public static List<Habbo> getUsersWorking(List<Habbo> habbos) {
        List<Habbo> habbosWorking = new ArrayList<>();
        for (Habbo habbo : habbos) {
            if (habbo.getPlayer().isWorking()) {
                habbosWorking.add(habbo);
            }
        }

        return habbosWorking;
    }

}
