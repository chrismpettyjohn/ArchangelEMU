package com.eu.websocket.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class WebSocketCodec extends MessageToMessageCodec<WebSocketFrame, ByteBuf> {

    private static final long PING_INTERVAL = TimeUnit.SECONDS.toNanos(5);
    private long lastPingTime = 0;

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // Keep connection alive with periodic pings
        long currentTime = System.nanoTime();
        if (currentTime - lastPingTime > PING_INTERVAL) {
            ctx.writeAndFlush(new PingWebSocketFrame());
            lastPingTime = currentTime;
        }

        // Normal message encoding
        out.add(new BinaryWebSocketFrame(in).retain());
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, WebSocketFrame in, List<Object> out) {
        // Handle pongs to keep connection alive
        if (in instanceof PongWebSocketFrame) {
            return;
        }

        // Handle pings by responding with pongs
        if (in instanceof PingWebSocketFrame) {
            ctx.writeAndFlush(new PongWebSocketFrame(in.content().retain()));
            return;
        }

        // Normal message decoding
        out.add(in.content().retain());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // Initialize ping timer when connection is established
        lastPingTime = System.nanoTime();
        super.channelActive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Prevent channel close on common exceptions
        if (shouldSuppressException(cause)) {
            return;
        }
        ctx.fireExceptionCaught(cause);
    }

    private boolean shouldSuppressException(Throwable cause) {
        // Ignore common websocket closure exceptions
        return cause.getMessage() != null && (
            cause.getMessage().contains("Connection reset by peer") ||
            cause.getMessage().contains("An established connection was aborted by the software in your host machine") ||
            cause.getMessage().contains("WebSocket ping timeout") ||
            cause.getMessage().contains("java.io.IOException: Broken pipe")
        );
    }
}