package com.us.roleplay.messages.incoming.corp;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.roleplay.commands.corp.CorpStartWorkCommand;

public class CorpStartWorkEvent extends MessageHandler {
    @Override
    public void handle() {
        new CorpStartWorkCommand().handle(this.client, new String[] {});
    }
}