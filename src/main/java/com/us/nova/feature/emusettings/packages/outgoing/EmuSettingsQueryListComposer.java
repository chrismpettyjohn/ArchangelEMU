package com.us.nova.feature.emusettings.packages.outgoing;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class EmuSettingsQueryListComposer extends MessageComposer  {

    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.emuSettingsQueryListComposer);

        List<String> properties = Emulator.getConfig().getAll();

        this.response.appendInt(properties.size());

        for (String property : properties) {
            this.response.appendString(property);
        }

        return this.response;
    }
}
