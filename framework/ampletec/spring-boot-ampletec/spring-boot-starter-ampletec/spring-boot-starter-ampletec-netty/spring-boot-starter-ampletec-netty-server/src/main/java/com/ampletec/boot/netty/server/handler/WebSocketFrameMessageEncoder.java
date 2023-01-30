package com.ampletec.boot.netty.server.handler;

import java.util.List;

import com.ampletec.commons.messaging.ResponseMessage;
import com.ampletec.commons.net.PackageOut;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrameEncoder;

public class WebSocketFrameMessageEncoder extends MessageToMessageEncoder<ResponseMessage>
		implements WebSocketFrameEncoder {

	@Override
	protected void encode(ChannelHandlerContext ctx, ResponseMessage msg, List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		try {
			PackageOut pkg = msg.response();
			pkg.commitChanges();
			ByteBuf bb = ctx.alloc().buffer(pkg.size());
			bb.writeBytes(pkg.array());
			out.add(new BinaryWebSocketFrame(bb));
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
