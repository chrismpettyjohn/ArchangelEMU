package com.eu.habbo.messages.outgoing.inventory;

import com.eu.habbo.habbohotel.rooms.items.entities.RoomItem;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class UnseenItemsComposer extends MessageComposer {
    private HashSet<RoomItem> itemsList;
    private RoomItem item;
    private int[] ids;
    private AddHabboItemCategory category;
    private Map<AddHabboItemCategory, List<Integer>> entries;

    public UnseenItemsComposer(HashSet<RoomItem> itemsList) {
        this.itemsList = itemsList;
        this.category = AddHabboItemCategory.OWNED_FURNI;
    }

    public UnseenItemsComposer(RoomItem item) {
        this.item = item;
        this.category = AddHabboItemCategory.OWNED_FURNI;
    }

    public UnseenItemsComposer(int[] ids, AddHabboItemCategory category) {
        this.ids = ids;
        this.category = category;
    }

    public UnseenItemsComposer(int id, AddHabboItemCategory category) {
        this.ids = new int[]{id};
        this.category = category;
    }

    public UnseenItemsComposer(Map<AddHabboItemCategory, List<Integer>> entries) {
        this.entries = entries;
    }

    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.unseenItemsComposer);

        if (this.ids != null) {
            this.response.appendInt(1);

            this.response.appendInt(this.category.number);
            this.response.appendInt(this.ids.length);
            for (int id : this.ids) {
                this.response.appendInt(id);
            }
        } else if (this.entries != null) {
            this.response.appendInt(this.entries.size());

            for (Map.Entry<AddHabboItemCategory, List<Integer>> item : this.entries.entrySet()) {
                this.response.appendInt(item.getKey().number);

                this.response.appendInt(item.getValue().size());
                for (int id : item.getValue()) {
                    this.response.appendInt(id);
                }
            }
        } else if (this.item == null) {
            this.response.appendInt(1);
            this.response.appendInt(1);
            this.response.appendInt(this.itemsList.size());
            for (RoomItem roomItem : this.itemsList) {
                this.response.appendInt(roomItem.getId());
            }
        } else {
            this.response.appendInt(1);
            this.response.appendInt(1);
            this.response.appendInt(1);
            this.response.appendInt(this.item.getId());
        }

        return this.response;
    }

    public enum AddHabboItemCategory {
        OWNED_FURNI(1),
        RENTED_FURNI(2),
        PET(3),
        BADGE(4),
        BOT(5),
        GAME(6);

        public final int number;

        AddHabboItemCategory(int number) {
            this.number = number;
        }
    }
}
