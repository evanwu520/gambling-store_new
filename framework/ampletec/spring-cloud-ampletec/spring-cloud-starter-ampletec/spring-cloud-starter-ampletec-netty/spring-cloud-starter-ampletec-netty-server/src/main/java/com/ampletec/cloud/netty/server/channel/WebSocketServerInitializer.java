package com.ampletec.cloud.netty.server.channel;

import com.ampletec.cloud.netty.server.NettyRequestAdapter;
import com.ampletec.cloud.netty.server.handler.WebSocketFrameInHandler;
import com.ampletec.cloud.netty.server.handler.WebSocketFrameMessageEncoder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {

	private static final String WEBSOCKET_PATH = "/websocket";

	public WebSocketServerInitializer(NettyRequestAdapter adapter) {
		m_raAdapter = adapter;
		this.path = WEBSOCKET_PATH;
	}

	public WebSocketServerInitializer(NettyRequestAdapter adapter, String path) {
		m_raAdapter = adapter;
		this.path = path;
	}

	private final String path;
	private final NettyRequestAdapter m_raAdapter;

	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new HttpServerCodec());
		pipeline.addLast(new ChunkedWriteHandler());
		pipeline.addLast(new HttpObjectAggregator(65536));
		// pipeline.addLast(new WebSocketHttpRequestHandler());
		pipeline.addLast(new WebSocketServerProtocolHandler(this.path, null, true));
		pipeline.addLast("frameEncoder", new WebSocketFrameMessageEncoder());
		pipeline.addLast(new WebSocketFrameInHandler(m_raAdapter));
	}
}