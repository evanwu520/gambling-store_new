package com.ampletec.boot.netty.server;

import io.netty.channel.Channel;

import java.util.HashSet;
import java.util.Set;

public class OnlineClientGroup {

	private static Set<Channel> m_clientGroup = new HashSet<Channel>(2000);

	public static void online(Channel channel) {
		m_clientGroup.add(channel);
	}
	
	public static void offline(Channel channel) {
		
	}
	
	public static void broadcast(Object msg) {
		for (Channel chl : m_clientGroup) {
			if (chl != null && chl.isOpen()) {
				chl.writeAndFlush(msg);
			}
		}
	}

	public static void disconnect(Channel chl) {
		if (chl != null) {
			m_clientGroup.remove(chl);
		}
	}
}
