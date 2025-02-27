package com.eu.habbo.messages.incoming.guild;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.guilds.Guild;
import com.eu.habbo.habbohotel.guilds.GuildState;
import com.eu.habbo.habbohotel.permissions.Permission;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.plugin.events.guilds.GuildChangedSettingsEvent;

public class UpdateGuildSettingsEvent extends MessageHandler {
    @Override
    public void handle() {
        int guildId = this.packet.readInt();

        Guild guild = Emulator.getGameEnvironment().getGuildManager().getGuild(guildId);

        if (guild != null) {
            if (guild.getOwnerId() == this.client.getHabbo().getHabboInfo().getId() || this.client.getHabbo().hasPermissionRight(Permission.ACC_GUILD_ADMIN)) {
                GuildChangedSettingsEvent settingsEvent = new GuildChangedSettingsEvent(guild, this.packet.readInt(), this.packet.readInt() == 0);
                Emulator.getPluginManager().fireEvent(settingsEvent);

                if (settingsEvent.isCancelled())
                    return;

                guild.setState(GuildState.valueOf(settingsEvent.getState()));
                guild.setRights(settingsEvent.isRights());

                Room room = Emulator.getGameEnvironment().getRoomManager().getActiveRoomById(guild.getRoomId());
                if(room != null) {
                    room.refreshGuild(guild);
                }

                guild.needsUpdate = true;

                Emulator.getThreading().run(guild);
            }
        }
    }
}
