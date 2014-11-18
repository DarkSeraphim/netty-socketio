/**
 * Copyright 2012 Nikita Koksharov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.corundumstudio.socketio.handler;

import static net.minecraft.util.io.netty.handler.codec.http.HttpVersion.HTTP_1_1;


import net.darkseraphim.util.Logger;
import net.minecraft.util.io.netty.channel.Channel;
import net.minecraft.util.io.netty.channel.ChannelFuture;
import net.minecraft.util.io.netty.channel.ChannelFutureListener;
import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
import net.minecraft.util.io.netty.channel.ChannelInboundHandlerAdapter;
import net.minecraft.util.io.netty.channel.ChannelHandler.Sharable;
import net.minecraft.util.io.netty.handler.codec.http.DefaultHttpResponse;
import net.minecraft.util.io.netty.handler.codec.http.FullHttpRequest;
import net.minecraft.util.io.netty.handler.codec.http.HttpResponse;
import net.minecraft.util.io.netty.handler.codec.http.HttpResponseStatus;
import net.minecraft.util.io.netty.handler.codec.http.QueryStringDecoder;

@Sharable
public class WrongUrlHandler extends ChannelInboundHandlerAdapter {

    private final Logger log = Logger.getLogger(getClass());

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest req = (FullHttpRequest) msg;
            Channel channel = ctx.channel();
            QueryStringDecoder queryDecoder = new QueryStringDecoder(req.getUri());

            HttpResponse res = new DefaultHttpResponse(HTTP_1_1, HttpResponseStatus.BAD_REQUEST);
            ChannelFuture f = channel.writeAndFlush(res);
            f.addListener(ChannelFutureListener.CLOSE);
            req.release();
            log.warn("Blocked wrong socket.io-context request! url: {}, params: {}, ip: {}", queryDecoder.path(), queryDecoder.parameters(), channel.remoteAddress());
        }
    }

}
