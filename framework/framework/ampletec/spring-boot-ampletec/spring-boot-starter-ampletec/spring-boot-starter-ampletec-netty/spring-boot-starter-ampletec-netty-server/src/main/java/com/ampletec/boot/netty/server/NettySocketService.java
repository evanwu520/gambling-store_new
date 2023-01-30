package com.ampletec.boot.netty.server;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.ampletec.boot.netty.server.autoconfigure.NettyServerProperties;
import com.ampletec.boot.netty.server.channel.TcpSocketServerInitializer;
import com.ampletec.boot.netty.server.channel.WebSocketServerInitializer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettySocketService {

	public NettySocketService(NettyServerProperties porp, NettyRequestAdapter raAdapter) throws UnknownHostException {
		this.properties = porp;
		this.nettyAdapter = raAdapter;
	}

	private NettyServerProperties properties;
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;
	private NettyRequestAdapter nettyAdapter;

	protected void startServer() throws Exception {
		ServerBootstrap b = new ServerBootstrap();
		bossGroup = new NioEventLoopGroup(properties.getBossSize());
		workerGroup = new NioEventLoopGroup(properties.getWorkerSize());
		b.group(bossGroup, workerGroup);
		b.channel(NioServerSocketChannel.class);
		if (this.properties.isWebsocket()) {
			b.childHandler(new WebSocketServerInitializer(nettyAdapter));
		} else {
			b.childHandler(new TcpSocketServerInitializer(nettyAdapter));
		}
//		b.bind(InetAddress.getLocalHost(), properties.getPort()).sync();
		b.bind(properties.getPort()).sync();
		log.info("{} start successful!! no host, port={}", this.properties.isWebsocket() ? "WebSocket": "Socket", properties.getPort());
	}

	public void shutdown() {
		workerGroup.shutdownGracefully();
		bossGroup.shutdownGracefully();
	}

	public void start() {
		try {
			startServer();
		} catch (Exception e) {
			log.error("tcp protocol launch failure !");
			log.error(ExceptionUtils.getStackTrace(e));
		}
	}

	public void stop() {
		// TODO Auto-generated method stub
		shutdown();
	}
}