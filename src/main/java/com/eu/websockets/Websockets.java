package com.eu.websockets;

import com.eu.habbo.Emulator;
import io.netty.util.AttributeKey;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Slf4j
@Getter
public class Websockets {
    public static final AttributeKey<String> WS_IP = AttributeKey.valueOf("WS_IP");
    private static final Logger LOGGER = LoggerFactory.getLogger(Websockets.class);

    public void load() throws InterruptedException {
        long millis = System.currentTimeMillis();

        Emulator.getConfig().register("websockets.whitelist", "localhost");
        Emulator.getConfig().register("ws.nitro.host", "0.0.0.0");
        Emulator.getConfig().register("ws.nitro.port", "2096");
        Emulator.getConfig().register("ws.nitro.ip.header", "");

        NetworkChannelInitializer wsChannelHandler = new NetworkChannelInitializer();
        Emulator.getGameServer().getServerBootstrap().childHandler(wsChannelHandler);

        Emulator.getGameServer().getServerBootstrap().bind(Emulator.getConfig().getValue("ws.nitro.host", "0.0.0.0"), Emulator.getConfig().getInt("ws.nitro.port", 2096)).sync();

        LOGGER.info("Websockets -> Loaded! (" + (System.currentTimeMillis() - millis) + " MS)");
    }

    public void dispose() {
        log.info("Websockets -> Disposed!");
    }

}
