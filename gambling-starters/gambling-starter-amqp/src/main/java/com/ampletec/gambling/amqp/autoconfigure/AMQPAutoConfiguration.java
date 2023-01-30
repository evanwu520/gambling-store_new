package com.ampletec.gambling.amqp.autoconfigure;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.ampletec.gambling.amqp.messaging.RabbitMQConstant;


@Configuration
@ComponentScan(basePackages = { "com.ampletec.gambling.amqp.messaging" })
public class AMQPAutoConfiguration {

	@Bean("userFanoutExchange")
	public FanoutExchange userFanoutExchange() {
		return new FanoutExchange(RabbitMQConstant.EXCHANGE_FANOUT_USER);
	}

	@Deprecated
	@Bean("gameTopicExchange")
	public TopicExchange gameTopicExchange() {
		return new TopicExchange(RabbitMQConstant.EXCHANGE_TOPIC_GAME);
	}

	@Bean("gameFanoutExchange")
	public FanoutExchange gameFanoutExchange() {
		return new FanoutExchange(RabbitMQConstant.EXCHANGE_FANOUT_GAME);
	}

	@Bean("robotFanoutExchange")
	public FanoutExchange robotFanoutExchange() {
		return new FanoutExchange(RabbitMQConstant.EXCHANGE_FANOUT_ROBOT);
	}

	@Bean("chatroomFanoutExchange")
	public FanoutExchange chatFanoutExchange() {
		return new FanoutExchange(RabbitMQConstant.IM_CHATROOM_EXCHANGE);
	}
}
