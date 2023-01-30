package com.ampletec.gambling.amqp.messaging;

import com.alibaba.fastjson.JSONObject;
import com.ampletec.commons.messaging.Message;

public interface ProducerService {

	void sendUser(Message msg);

	void sendGame(Message msg);

	void sendRobot(Message msg);

	void sendDealer(Object msg);

	void sendChatroom(Object msg);

	void dispatchPlayerEvent(Object msg);

	void produceGameFlow(JSONObject object);
}
