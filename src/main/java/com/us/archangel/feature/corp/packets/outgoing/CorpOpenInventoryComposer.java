package com.us.archangel.feature.corp.packets.outgoing;

import com.eu.habbo.habbohotel.rooms.items.entities.RoomItem;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.archangel.corp.model.CorpModel;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CorpOpenInventoryComposer extends MessageComposer {
    private final RoomItem roomItem;
    private final CorpModel corp;

    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.corpOpenInventoryComposer);
        this.response.appendInt(this.roomItem.getId());
        this.response.appendInt(this.corp.getId());
        return this.response;
    }
}
