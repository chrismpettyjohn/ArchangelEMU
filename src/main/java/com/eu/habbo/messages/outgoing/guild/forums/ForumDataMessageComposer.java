package com.eu.habbo.messages.outgoing.guild.forums;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.guilds.Guild;
import com.eu.habbo.habbohotel.guilds.GuildMember;
import com.eu.habbo.habbohotel.guilds.GuildRank;
import com.eu.habbo.habbohotel.guilds.forums.ForumThread;
import com.eu.habbo.habbohotel.guilds.forums.ForumThreadComment;
import com.eu.habbo.habbohotel.permissions.Permission;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.eu.habbo.messages.outgoing.handshake.ErrorReportComposer;
import gnu.trove.set.hash.THashSet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@Slf4j
@Getter
@AllArgsConstructor
public class ForumDataMessageComposer extends MessageComposer {


    private final Guild guild;
    private Habbo habbo;

    public static void serializeForumData(ServerMessage response, Guild guild, Habbo habbo) {

        final THashSet<ForumThread> forumThreads = ForumThread.getByGuildId(guild.getId());
        int lastSeenAt = 0;

        int totalComments = 0;
        int newComments = 0;
        int totalThreads = 0;
        ForumThreadComment lastComment = null;

        synchronized (forumThreads) {
            for (ForumThread thread : forumThreads) {
                totalThreads++;
                totalComments += thread.getPostsCount();

                ForumThreadComment comment = thread.getLastComment();
                if (comment != null && (lastComment == null || lastComment.getCreatedAt() < comment.getCreatedAt())) {
                    lastComment = comment;
                }
            }
        }

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection(); PreparedStatement statement = connection.prepareStatement(
                "SELECT COUNT(*) " +
                        "FROM guilds_forums_threads A " +
                        "JOIN ( " +
                        "SELECT * " +
                        "FROM `guilds_forums_comments` " +
                        "WHERE `id` IN ( " +
                        "SELECT id " +
                        "FROM `guilds_forums_comments` B " +
                        "ORDER BY B.`id` ASC " +
                        ") " +
                        "ORDER BY `id` DESC " +
                        ") B ON A.`id` = B.`thread_id` " +
                        "WHERE A.`guild_id` = ? AND B.`created_at` > ?"
        )) {
            statement.setInt(1, guild.getId());
            statement.setInt(2, lastSeenAt);

            ResultSet set = statement.executeQuery();
            while (set.next()) {
                newComments = set.getInt(1);
            }
        } catch (SQLException e) {
            log.error("Caught SQL exception", e);
        }

        response.appendInt(guild.getId());

        response.appendString(guild.getName());
        response.appendString(guild.getDescription());
        response.appendString(guild.getBadge());

        response.appendInt(totalThreads);
        response.appendInt(0); //Rating

        response.appendInt(totalComments); //Total comments
        response.appendInt(newComments); //Unread comments

        response.appendInt(lastComment != null ? lastComment.getThreadId() : -1);
        response.appendInt(lastComment != null ? lastComment.getUserId() : -1);
        response.appendString(lastComment != null && lastComment.getHabbo() != null ? lastComment.getHabbo().getHabboInfo().getUsername() : "");
        response.appendInt(lastComment != null ? Emulator.getIntUnixTimestamp() - lastComment.getCreatedAt() : 0);
    }

    @Override
    protected ServerMessage composeInternal() {

        try {
            this.response.init(Outgoing.forumDataMessageComposer);
            serializeForumData(this.response, guild, habbo);

            GuildMember member = Emulator.getGameEnvironment().getGuildManager().getGuildMember(guild, habbo);
            boolean isAdmin = member != null && (member.getRank().getType() < GuildRank.MEMBER.getType() || guild.getOwnerId() == this.habbo.getHabboInfo().getId());
            boolean isStaff = this.habbo.hasPermissionRight(Permission.ACC_MODTOOL_TICKET_Q);

            String errorRead = "";
            String errorPost = "";
            String errorStartThread = "";
            String errorModerate = "";

            if (guild.canReadForum().getState() == 1 && member == null && !isStaff) {
                errorRead = "not_member";
            } else if (guild.canReadForum().getState() == 2 && !isAdmin && !isStaff) {
                errorRead = "not_admin";
            }

            if (guild.canPostMessages().getState() == 1 && member == null && !isStaff) {
                errorPost = "not_member";
            } else if (guild.canPostMessages().getState() == 2 && !isAdmin && !isStaff) {
                errorPost = "not_admin";
            } else if (guild.canPostMessages().getState() == 3 && guild.getOwnerId() != this.habbo.getHabboInfo().getId() && !isStaff) {
                errorPost = "not_owner";
            }

            if (guild.canPostThreads().getState() == 1 && member == null && !isStaff) {
                errorStartThread = "not_member";
            } else if (guild.canPostThreads().getState() == 2 && !isAdmin && !isStaff) {
                errorStartThread = "not_admin";
            } else if (guild.canPostThreads().getState() == 3 && guild.getOwnerId() != this.habbo.getHabboInfo().getId() && !isStaff) {
                errorStartThread = "not_owner";
            }

            if (guild.canModForum().getState() == 3 && guild.getOwnerId() != this.habbo.getHabboInfo().getId() && !isStaff) {
                errorModerate = "not_owner";
            } else if (!isAdmin && !isStaff) {
                errorModerate = "not_admin";
            }

            this.response.appendInt(guild.canReadForum().getState());
            this.response.appendInt(guild.canPostMessages().getState());
            this.response.appendInt(guild.canPostThreads().getState());
            this.response.appendInt(guild.canModForum().getState());
            this.response.appendString(errorRead);
            this.response.appendString(errorPost);
            this.response.appendString(errorStartThread);
            this.response.appendString(errorModerate);
            this.response.appendString(""); //citizen
            this.response.appendBoolean(guild.getOwnerId() == this.habbo.getHabboInfo().getId()); //Forum Settings
            if (guild.canModForum().getState() == 3) {
                this.response.appendBoolean(guild.getOwnerId() == this.habbo.getHabboInfo().getId() || isStaff);
            }
            else {
                this.response.appendBoolean(guild.getOwnerId() == this.habbo.getHabboInfo().getId() || isStaff || isAdmin); //Can Mod (staff)
            }
        } catch (Exception e) {
            log.error("Caught SQL exception", e);
            return new ErrorReportComposer(500).compose();
        }

        return this.response;
    }
}