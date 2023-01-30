package com.ampletec.boot.netty.server.utils;

import io.netty.channel.Channel;

import com.ampletec.boot.netty.server.Client;
import com.ampletec.commons.messaging.ErrorMessage;
import com.ampletec.commons.messaging.RequestMessage;

public abstract class AccessValidator<C extends Client<?>, M extends RequestMessage> extends Validator<M> {
	
	protected abstract C transform(Channel channel) ;

	public boolean validate(Channel channel, M msg) throws Exception {
		C client = this.transform(channel);
		if (!client.isAuth()) {
			channel.writeAndFlush(ErrorMessage.UNAUTHORIZED);
			return false;
		}
		return validate(client, msg);
	}

	public abstract boolean validate(C client, M msg) throws Exception;
	
	public void onInternalError(C client) {
		client.writeAndFlush(ErrorMessage.INTERNAL_SERVER_ERROR);
	}
}
