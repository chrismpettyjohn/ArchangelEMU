package com.eu.habbo.habbohotel.items.interactions;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.items.YoutubeManager;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.items.entities.RoomItem;
import com.eu.habbo.habbohotel.rooms.entities.units.RoomUnit;
import com.eu.habbo.habbohotel.users.HabboInfo;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.rooms.items.youtube.YoutubeDisplayVideoMessageComposer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ScheduledFuture;

public class InteractionYoutubeTV extends RoomItem {
    public YoutubeManager.YoutubePlaylist currentPlaylist = null;
    public YoutubeManager.YoutubeVideo currentVideo = null;
    public int startedWatchingAt = 0;
    public int offset = 0;
    public boolean playing = true;
    public ScheduledFuture autoAdvance = null;

    public InteractionYoutubeTV(ResultSet set, Item baseItem) throws SQLException {
        super(set, baseItem);
    }

    public InteractionYoutubeTV(int id, HabboInfo ownerInfo, Item item, String extradata, int limitedStack, int limitedSells) {
        super(id, ownerInfo, item, extradata, limitedStack, limitedSells);
    }

    @Override
    public boolean canWalkOn(RoomUnit roomUnit, Room room, Object[] objects) {
        return false;
    }

    @Override
    public boolean isWalkable() {
        return false;
    }

    @Override
    public void onWalk(RoomUnit roomUnit, Room room, Object[] objects) {

    }

    @Override
    public void serializeExtradata(ServerMessage serverMessage) {
        if (this.getExtraData().length() == 0)
            this.setExtraData("");

        serverMessage.appendInt(1 + (this.isLimited() ? 256 : 0));
        serverMessage.appendInt(1);
        serverMessage.appendString("THUMBNAIL_URL");
        if (this.currentVideo == null) {
            serverMessage.appendString("");
        } else {
            serverMessage.appendString(Emulator.getConfig().getValue("imager.url.youtube").replace("%video%", this.currentVideo.getId()));
        }

        super.serializeExtradata(serverMessage);
    }

    @Override
    public void onPickUp(Room room) {
        super.onPickUp(room);

        if (this.autoAdvance != null) {
            this.cancelAdvancement();
        }

        this.currentVideo = null;
        this.currentPlaylist = null;
        this.startedWatchingAt = 0;
        this.offset = 0;
    }

    public void cancelAdvancement() {
        if (this.autoAdvance == null) return;

        this.autoAdvance.cancel(true);
        this.autoAdvance = null;
    }

    @Override
    public void onClick(GameClient client, Room room, Object[] objects) throws Exception {
        super.onClick(client, room, objects);

        if (this.currentVideo != null) {
            int startTime = this.offset;
            if (this.playing) startTime += Emulator.getIntUnixTimestamp() - this.startedWatchingAt;
            client.sendResponse(new YoutubeDisplayVideoMessageComposer(this.getId(), this.currentVideo, this.playing, startTime));
        }
    }
}