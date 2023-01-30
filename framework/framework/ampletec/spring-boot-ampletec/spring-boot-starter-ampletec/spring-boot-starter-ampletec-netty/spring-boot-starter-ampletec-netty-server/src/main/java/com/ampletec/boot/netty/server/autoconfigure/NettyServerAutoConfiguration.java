package com.ampletec.boot.netty.server.autoconfigure;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ampletec.boot.netty.server.NettyRequestAdapter;
import com.ampletec.boot.netty.server.NettySocketService;

import io.netty.channel.Channel;

@Configuration
@EnableConfigurationProperties(NettyServerProperties.class)
public class NettyServerAutoConfiguration {

	@Autowired
	private NettyServerProperties properties;
	
	@Bean(name = "nettySocketService",initMethod = "start", destroyMethod = "shutdown")
	@ConditionalOnMissingBean
	public NettySocketService nettySocketService() throws UnknownHostException {
		return new NettySocketService(properties, nettyRequestAdapter());
	}
	
	@Bean
	@ConditionalOnMissingBean
	public NettyRequestAdapter nettyRequestAdapter() {
		return new NettyRequestAdapter() {

			@Override
			public void handleClose(Channel channel) throws Exception {
				// TODO Auto-generated method stub
				
			}
		};
	}
}
