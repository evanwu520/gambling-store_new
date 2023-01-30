package com.ampletec.gambling.amqp.messaging.impl;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.ampletec.commons.messaging.Message;
import com.ampletec.gambling.amqp.messaging.RabbitMQConstant;

@Component
public class RabbitMQProducerImpl {

	@Autowired
	private AmqpTemplate amqpTemplate;

	public void sendUser(Message msg) {
		amqpTemplate.convertAndSend(RabbitMQConstant.EXCHANGE_FANOUT_USER, "", msg);
	}

	public void sendGame(Message msg) {
		amqpTemplate.convertAndSend(RabbitMQConstant.EXCHANGE_FANOUT_GAME, "", msg);
	}

	public void sendRobot(Message msg) {
		amqpTemplate.convertAndSend(RabbitMQConstant.EXCHANGE_FANOUT_ROBOT, "", msg);
	}

	public void produceGameFlow(JSONObject object) {
		amqpTemplate.convertAndSend(RabbitMQConstant.EXCHANGE_GAMBLING_FLOWSYNC_DRIVER, "", object);
	}

	public void sendDealer(Object msg) {
		amqpTemplate.convertAndSend(RabbitMQConstant.EXCHANGE_FANOUT_TODEALER, "", msg);
	}

	public void sendChatroom(Object msg) {
		amqpTemplate.convertAndSend(RabbitMQConstant.IM_CHATROOM_EXCHANGE, "", msg);
	}

	public void dispatchPlayerEvent(Object msg) {
		amqpTemplate.convertAndSend(RabbitMQConstant.EXCHANGE_FANOUT_PLAYER_EVENT, "", msg);
	}
}
