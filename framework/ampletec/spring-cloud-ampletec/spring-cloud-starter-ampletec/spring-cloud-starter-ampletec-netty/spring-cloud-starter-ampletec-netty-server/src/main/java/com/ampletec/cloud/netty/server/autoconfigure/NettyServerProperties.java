package com.ampletec.cloud.netty.server.autoconfigure;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = NettyServerProperties.NETTY_PREFIX)
public class NettyServerProperties {
	
	public static final String NETTY_PREFIX = "spring.netty";

	private int port;
	private int bossSize;
	private int workerSize;

	private String path;
	public boolean isWebsocket() {
		return StringUtils.isEmpty(path) == false;
	}
}
