package com.ampletec.cloud.netty.server.handler;

import com.ampletec.commons.messaging.ResponseMessage;
import com.ampletec.commons.net.PackageOut;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 写入消息转换
 * 
 * @author john
 * 
 */
public class MessageEncoder extends MessageToByteEncoder<ResponseMessage> {

	@Override
	protected void encode(ChannelHandlerContext ctx, ResponseMessage msg, ByteBuf out) throws Exception {
		// TODO Auto-generated method stub
		try {
			PackageOut pkg = msg.response();
			pkg.commitChanges();
			out.writeBytes(pkg.array());
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
