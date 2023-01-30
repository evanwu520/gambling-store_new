package com.ampletec.cloud.netty.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

import com.ampletec.commons.messaging.ErrorMessage;
import com.ampletec.commons.messaging.ResponseMessage;

public abstract class Client<T> {
	
	public static String getIPAddress(Channel channel) {
		return channel.remoteAddress().toString().substring(1).split(":")[0];
	}

	private static AttributeKey<String> remoteAddrAttrKey = AttributeKey.valueOf("remote_addr");
	public static void setRemoteAddr(Channel channel, String realip) {
		channel.attr(remoteAddrAttrKey).set(realip);
	}
	
	public static String getRemoteAddr(Channel channel) {
		return channel.attr(remoteAddrAttrKey).get();
	}
	
	private AttributeKey<T> sessionAttrKey = AttributeKey.valueOf("session");

	public Client(Channel channel) {
		this.channel = channel;

		this.sessionAttr = channel.attr(this.sessionAttrKey);
		if (this.sessionAttr != null) {
			this.session = channel.attr(this.sessionAttrKey).get();
		}
	}

	protected Channel channel;

	protected Attribute<T> sessionAttr;

	protected T session;

	public abstract boolean isAuth();
	
	public Channel channel() {
		return this.channel;
	}

	public void close() {
		this.channel.close();
		this.cleanChannel();
	}

	protected abstract void cleanChannel();

	public void cache(T session) {
		this.session = session;
		this.sessionAttr.set(session);
	}
	
	public abstract int getIdentify() ;

	public T getSession() {
		return this.session;
	}

	public ChannelFuture writeAndFlush(ResponseMessage message) {
		return this.channel.writeAndFlush(message);
	}
	
	public ChannelFuture write(ResponseMessage message) {
		return this.channel.write(message);
	}

	public void onError(short errCode) {
		writeAndFlush(new ErrorMessage(errCode));
	}
}
