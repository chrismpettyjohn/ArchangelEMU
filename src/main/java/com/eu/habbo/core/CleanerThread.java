package com.eu.habbo.core;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.guilds.forums.ForumThread;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.incoming.friends.HabboSearchEvent;
import com.eu.habbo.messages.incoming.navigator.RoomTextSearchEvent;
import com.eu.habbo.messages.outgoing.users.UserObjectComposer;
import com.eu.habbo.threading.runnables.AchievementUpdater;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

@Slf4j
public class CleanerThread implements Runnable {

    private static final int DELAY = 10000;
    private static final int REMOVE_INACTIVE_GUILDS = 60;
    private static final int REMOVE_INACTIVE_TOURS = 600;
    private static final int SAVE_ERROR_LOGS = 30;
    private static final int CLEAR_CACHED_VALUES = 60 * 60;
    private static final int CALLBACK_TIME = 60 * 15;

    private static int LAST_INACTIVE_GUILDS_CLEARED = Emulator.getIntUnixTimestamp();
    private static int LAST_INACTIVE_TOURS_CLEARED = Emulator.getIntUnixTimestamp();
    private static int LAST_ERROR_LOGS_SAVED = Emulator.getIntUnixTimestamp();
    private static int LAST_DAILY_REFILL = Emulator.getIntUnixTimestamp();
    private static int LAST_CALLBACK = Emulator.getIntUnixTimestamp();
    private static int LAST_HABBO_CACHE_CLEARED = Emulator.getIntUnixTimestamp();

    public CleanerThread() {
        this.databaseCleanup();
        Emulator.getThreading().run(this, DELAY);

        Emulator.getThreading().run(new AchievementUpdater());

        //  Emulator.getThreading().run(new HTTPVersionCheck(), 10000);
    }

    @Override
    public void run() {
        Emulator.getThreading().run(this, DELAY);

        int time = Emulator.getIntUnixTimestamp();

        if (time - LAST_INACTIVE_GUILDS_CLEARED > REMOVE_INACTIVE_GUILDS) {
            Emulator.getGameEnvironment().getGuildManager().clearInactiveGuilds();
            ForumThread.clearCache();
            LAST_INACTIVE_GUILDS_CLEARED = time;
        }

        if (time - LAST_INACTIVE_TOURS_CLEARED > REMOVE_INACTIVE_TOURS) {
            Emulator.getGameEnvironment().getGuideManager().cleanup();
            LAST_INACTIVE_TOURS_CLEARED = time;
        }

        if (time - LAST_ERROR_LOGS_SAVED > SAVE_ERROR_LOGS) {
            Emulator.getDatabaseLogger().save();
            LAST_ERROR_LOGS_SAVED = time;
        }

        if (time - LAST_CALLBACK > CALLBACK_TIME) {
            //  Emulator.getThreading().run(new HTTPPostStatus());
            LAST_CALLBACK = time;
        }

        if (time - LAST_DAILY_REFILL > Emulator.getConfig().getInt("hotel.refill.daily")) {
            this.refillDailyRespects();
            LAST_DAILY_REFILL = time;
        }

        if (time - LAST_HABBO_CACHE_CLEARED > CLEAR_CACHED_VALUES) {
            this.clearCachedValues();
            LAST_HABBO_CACHE_CLEARED = time;
        }

        RoomTextSearchEvent.cachedResults.clear();
        HabboSearchEvent.cachedResults.clear();
    }


    void databaseCleanup() {
        this.refillDailyRespects();

        int time = Emulator.getIntUnixTimestamp();
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("UPDATE users SET online = '0' WHERE online = '1'");
                statement.execute("UPDATE rooms SET users = '0' WHERE users > 0");
                statement.execute("DELETE FROM room_mutes WHERE ends < " + time);
                statement.execute("DELETE FROM room_bans WHERE ends < " + time);
                statement.execute("DELETE users_favorite_rooms FROM users_favorite_rooms LEFT JOIN rooms ON room_id = rooms.id WHERE rooms.id IS NULL");
            }

            try (PreparedStatement statement = connection.prepareStatement("UPDATE users_effects SET total = total - 1 WHERE activation_timestamp + duration < ? AND activation_timestamp > 0 AND duration > 0")) {
                statement.setInt(1, Emulator.getIntUnixTimestamp());
                statement.execute();
            }

            try (Statement statement = connection.createStatement()) {
                statement.execute("DELETE FROM users_effects WHERE total <= 0");
            }
        } catch (SQLException e) {
            log.error("Caught SQL exception", e);
        }

        log.info("Database -> Cleaned!");
    }

    public void refillDailyRespects() {
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection(); PreparedStatement statement = connection.prepareStatement("UPDATE users_settings SET daily_respect_points = ?, daily_pet_respect_points = ?")) {
            statement.setInt(1, Emulator.getConfig().getInt("hotel.daily.respect"));
            statement.setInt(2, Emulator.getConfig().getInt("hotel.daily.respect.pets"));
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Caught SQL exception", e);
        }

        if (Emulator.isReady) {
            for (Habbo habbo : Emulator.getGameEnvironment().getHabboManager().getOnlineHabbos().values()) {
                habbo.getHabboStats().setRespectPointsToGive(Emulator.getConfig().getInt("hotel.daily.respect"));
                habbo.getHabboStats().setPetRespectPointsToGive(Emulator.getConfig().getInt("hotel.daily.respect.pets"));
                habbo.getClient().sendResponse(new UserObjectComposer(habbo));
            }
        }
    }

    private void clearCachedValues() {
        Habbo habbo;
        for (Map.Entry<Integer, Habbo> map : Emulator.getGameEnvironment().getHabboManager().getOnlineHabbos().entrySet()) {
            habbo = map.getValue();

            try {
                if (habbo != null) {
                    habbo.clearCaches();
                }
            } catch (Exception e) {
                log.error("Caught exception", e);
            }
        }
    }
}
