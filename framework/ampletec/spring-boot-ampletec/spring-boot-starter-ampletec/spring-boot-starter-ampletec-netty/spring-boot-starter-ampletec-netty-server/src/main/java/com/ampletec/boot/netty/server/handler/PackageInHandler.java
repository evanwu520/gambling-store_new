package com.ampletec.boot.netty.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.ampletec.boot.netty.server.NettyRequestAdapter;
import com.ampletec.commons.messaging.ResponseMessage;
import com.ampletec.commons.net.PackageIn;
import com.ampletec.commons.net.PackageOut;

public class PackageInHandler extends SimpleChannelInboundHandler<PackageIn> {

	public PackageInHandler(NettyRequestAdapter adapter) {
		nettyAdapter = adapter;
	}

	private final NettyRequestAdapter nettyAdapter;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, PackageIn in) throws Exception {
		if (in.getType() == -1) {
			ctx.writeAndFlush(new ResponseMessage(in.getType()) {
				private static final long serialVersionUID = 1L;
				@Override
				protected void writePackage(PackageOut out) throws Throwable {}
			});
		} else {
			try {
				nettyAdapter.handleRequest(ctx.channel(), in);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		nettyAdapter.handleClose(ctx.channel());
		ctx.close();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// channel失效处理,客户端下线或者强制退出等任何情况都触发这个方法
		nettyAdapter.handleClose(ctx.channel());
		super.channelInactive(ctx);
	}
}