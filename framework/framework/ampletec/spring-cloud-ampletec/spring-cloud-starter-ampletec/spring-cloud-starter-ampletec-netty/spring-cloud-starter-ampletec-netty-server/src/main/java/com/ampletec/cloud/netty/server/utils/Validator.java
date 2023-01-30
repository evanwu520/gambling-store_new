package com.ampletec.cloud.netty.server.utils;

import io.netty.channel.Channel;

import com.ampletec.commons.messaging.RequestMessage;

public abstract class Validator<M extends RequestMessage> {
	public abstract boolean validate(Channel channel, M msg) throws Exception;
}
