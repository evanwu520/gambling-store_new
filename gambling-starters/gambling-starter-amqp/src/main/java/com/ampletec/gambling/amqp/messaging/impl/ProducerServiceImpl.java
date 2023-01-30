package com.ampletec.gambling.amqp.messaging.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ampletec.commons.messaging.Message;
import com.ampletec.gambling.amqp.messaging.ProducerService;

@Service
public class ProducerServiceImpl implements ProducerService {

	@Autowired
	private RabbitMQProducerImpl rabbitMQProducer;

	@Override
	public void sendUser(Message msg) {
		rabbitMQProducer.sendUser(msg);
	}

	@Override
	public void sendGame(Message msg) {
		rabbitMQProducer.sendGame(msg);
	}

	@Override
	public void sendRobot(Message msg) {
		rabbitMQProducer.sendRobot(msg);
	}

	@Override
	public void produceGameFlow(JSONObject object) {
		rabbitMQProducer.produceGameFlow(object);
	}

	@Override
	public void sendChatroom(Object msg) {
		rabbitMQProducer.sendChatroom(msg);
	}

	@Override
	public void sendDealer(Object msg) {
		rabbitMQProducer.sendDealer(msg);
	}

	@Override
	public void dispatchPlayerEvent(Object msg) {
		rabbitMQProducer.dispatchPlayerEvent(msg);
	}
}
