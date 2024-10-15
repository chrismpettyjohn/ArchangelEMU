package com.us.roleplay.guilds;

import com.eu.habbo.habbohotel.guilds.Guild;
import com.us.roleplay.guilds.inventory.ItemsComponent;
import com.us.roleplay.guilds.inventory.WeaponsComponent;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class GuildInventory {
    public static int MAXIMUM_ITEMS = 10000;
    private final Guild guild;
    private ItemsComponent itemsComponent;
    private WeaponsComponent weaponsComponent;

    public GuildInventory(Guild guild) {
        this.guild = guild;
        try {
            this.itemsComponent = new ItemsComponent(this.guild);
        } catch (Exception e) {
            log.error("Caught exception", e);
        }

        try {
            this.weaponsComponent = new WeaponsComponent(this.guild);
        } catch (Exception e) {
            log.error("Caught exception", e);
        }
    }



    public void dispose() {
        this.itemsComponent.dispose();
        this.weaponsComponent.dispose();
        this.itemsComponent = null;
    }
}
