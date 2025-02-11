package com.eu.habbo.messages.incoming.modtool;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.modtool.ModToolIssue;
import com.eu.habbo.habbohotel.modtool.ScripterManager;
import com.eu.habbo.habbohotel.permissions.Permission;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.incoming.MessageHandler;

public class CloseIssuesEvent extends MessageHandler {
    @Override
    public void handle() {
        if (this.client.getHabbo().hasPermissionRight(Permission.ACC_SUPPORTTOOL)) {
            int state = this.packet.readInt();
            int something = this.packet.readInt();
            int ticketId = this.packet.readInt();

            ModToolIssue issue = Emulator.getGameEnvironment().getModToolManager().getTicket(ticketId);

            if (issue == null || issue.modId != this.client.getHabbo().getHabboInfo().getId())
                return;

            Habbo sender = Emulator.getGameEnvironment().getHabboManager().getHabbo(issue.senderId);

            switch (state) {
                case 1 -> Emulator.getGameEnvironment().getModToolManager().closeTicketAsUseless(issue, sender);
                case 2 -> Emulator.getGameEnvironment().getModToolManager().closeTicketAsAbusive(issue, sender);
                case 3 -> Emulator.getGameEnvironment().getModToolManager().closeTicketAsHandled(issue, sender);
            }
        } else {
            ScripterManager.scripterDetected(this.client, Emulator.getTexts().getValue("scripter.warning.modtools.ticket.close").replace("%username%", this.client.getHabbo().getHabboInfo().getUsername()));
        }
    }
}
