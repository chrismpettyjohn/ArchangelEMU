package com.eu.habbo.threading.runnables;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.items.interactions.InteractionWiredEffect;
import com.eu.habbo.habbohotel.rooms.Room;
import lombok.AllArgsConstructor;

@AllArgsConstructor
class WiredRepeatEffectTask implements Runnable {
    private final InteractionWiredEffect effect;
    private final Room room;
    private final int delay;


    @Override
    public void run() {
        if (!Emulator.isShuttingDown && Emulator.isReady) {
            if (this.room != null) {
                if (this.room.getRoomInfo().getId() == this.effect.getRoomId()) {
                    this.effect.execute(null, this.room, null);

                    Emulator.getThreading().run(this, this.delay);
                }
            }
        }
    }
}
