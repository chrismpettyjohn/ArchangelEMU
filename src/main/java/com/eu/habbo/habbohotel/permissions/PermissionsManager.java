package com.eu.habbo.habbohotel.permissions;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.plugin.HabboPlugin;
import gnu.trove.map.hash.THashMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
public class PermissionsManager {
    private final TIntObjectHashMap<Rank> ranks;
    private final TIntIntHashMap enables;
    private final THashMap<String, List<Rank>> badges;

    public PermissionsManager() {
        long millis = System.currentTimeMillis();
        this.ranks = new TIntObjectHashMap<>();
        this.enables = new TIntIntHashMap();
        this.badges = new THashMap<>();

        this.reload();

        log.info("Permissions Manager -> Loaded! (" + (System.currentTimeMillis() - millis) + " MS)");
    }

    public void reload() {
        this.loadPermissions();
        this.loadEnables();
    }

    private void loadPermissions() {
        this.badges.clear();

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection(); Statement statement = connection.createStatement(); ResultSet set = statement.executeQuery("SELECT * FROM permissions ORDER BY id ASC")) {
            while (set.next()) {
                Rank rank;
                if (!this.ranks.containsKey(set.getInt("id"))) {
                    rank = new Rank(set);
                    this.ranks.put(set.getInt("id"), rank);
                } else {
                    rank = this.ranks.get(set.getInt("id"));
                    rank.load(set);
                }

                if (!rank.getBadge().isEmpty()) {
                    if (!this.badges.containsKey(rank.getBadge())) {
                        this.badges.put(rank.getBadge(), new ArrayList<>());
                    }

                    this.badges.get(rank.getBadge()).add(rank);
                }
            }
        } catch (SQLException e) {
            log.error("Caught SQL exception", e);
        }
    }

    private void loadEnables() {
        synchronized (this.enables) {
            this.enables.clear();

            try (Connection connection = Emulator.getDatabase().getDataSource().getConnection(); Statement statement = connection.createStatement(); ResultSet set = statement.executeQuery("SELECT * FROM special_enables")) {
                while (set.next()) {
                    this.enables.put(set.getInt("effect_id"), set.getInt("min_rank"));
                }
            } catch (SQLException e) {
                log.error("Caught SQL exception", e);
            }
        }
    }


    public boolean rankExists(int rankId) {
        return this.ranks.containsKey(rankId);
    }


    public Rank getRank(int rankId) {
        return this.ranks.get(rankId);
    }


    public Rank getRankByName(String rankName) {
        for (Rank rank : this.ranks.valueCollection()) {
            if (rank.getName().equalsIgnoreCase(rankName))
                return rank;
        }

        return null;
    }


    public boolean isEffectBlocked(int effectId, int rank) {
        return this.enables.contains(effectId) && this.enables.get(effectId) > rank;
    }


    public boolean hasPermission(Habbo habbo, String permission) {
        return this.hasPermission(habbo, permission, false);
    }


    public boolean hasPermission(Habbo habbo, String permission, boolean withRoomRights) {
        if (!this.hasPermission(habbo.getHabboInfo().getRank(), permission, withRoomRights)) {
            for (HabboPlugin plugin : Emulator.getPluginManager().getPlugins()) {
                if (plugin.hasPermission(habbo, permission)) {
                    return true;
                }
            }

            return false;
        }

        return true;
    }


    public boolean hasPermission(Rank rank, String permission, boolean withRoomRights) {
        return rank.hasPermission(permission, withRoomRights);
    }

    public Set<String> getStaffBadges() {
        return this.badges.keySet();
    }

    public List<Rank> getRanksByBadgeCode(String code) {
        return this.badges.get(code);
    }

    public List<Rank> getAllRanks() {
        return new ArrayList<>(this.ranks.valueCollection());
    }
}
