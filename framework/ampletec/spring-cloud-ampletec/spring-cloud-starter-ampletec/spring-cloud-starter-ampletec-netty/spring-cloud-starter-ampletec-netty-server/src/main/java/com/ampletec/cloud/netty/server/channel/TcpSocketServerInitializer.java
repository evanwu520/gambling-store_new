package com.ampletec.cloud.netty.server.channel;

import com.ampletec.cloud.netty.server.NettyRequestAdapter;
import com.ampletec.cloud.netty.server.OnlineClientGroup;
import com.ampletec.cloud.netty.server.handler.MessageEncoder;
import com.ampletec.cloud.netty.server.handler.PackageInDecoder;
import com.ampletec.cloud.netty.server.handler.PackageInHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class TcpSocketServerInitializer extends ChannelInitializer<SocketChannel> {
	
	public TcpSocketServerInitializer(NettyRequestAdapter adapter) {
		m_raAdapter = adapter;
	}
	private final NettyRequestAdapter m_raAdapter;
	
	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		// 日志文件
		// pipeline.addLast(new LoggingHandler(LogLevel.INFO));
		// 自定义消息转换
		pipeline.addLast(new ChannelInboundHandlerAdapter(){
			@Override
		    public void channelActive(ChannelHandlerContext ctx) {
		        OnlineClientGroup.online(ctx.channel());
		    }
			@Override
			public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
				// log.warn("Unexpected exception from downstream.", cause);
				OnlineClientGroup.disconnect(ctx.channel());
				ctx.close();
			}

			@Override
			public void channelInactive(ChannelHandlerContext ctx) throws Exception {
				// channel失效处理,客户端下线或者强制退出等任何情况都触发这个方法
				OnlineClientGroup.disconnect(ctx.channel());
				super.channelInactive(ctx);
			}
		});
		pipeline.addLast("frameDecoder", new PackageInDecoder());
		pipeline.addLast("frameEncoder", new MessageEncoder());
		if(m_raAdapter != null) pipeline.addLast("messageHandler", new PackageInHandler(m_raAdapter));
	}
}
