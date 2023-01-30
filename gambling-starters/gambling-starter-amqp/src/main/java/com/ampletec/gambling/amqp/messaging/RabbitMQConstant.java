package com.ampletec.gambling.amqp.messaging;

import java.util.Random;
import java.util.UUID;

import com.ampletec.commons.lang.SplitCharacter;

public class RabbitMQConstant {	

	public static final String EXCHANGE_GAMBLING_FLOWSYNC_DRIVER = "gambling.flowsync.driver";
	
	public static final String TOPIC_ROUTINGKEY_GAME = "gambling.routing-key.game.";

	public static final String EXCHANGE_TOPIC_USER = "gambling.EXCHANGE_TOPIC_USER";
	public static final String EXCHANGE_FANOUT_USER = "gambling.EXCHANGE_FANOUT_USER";
	public static final String QUEUE_FANOUT_USER = "gambling.QUEUE_FANOUT_USER";

	public static final String QUEUE_GAME_PLAYING_ROBOT = "gambling.QUEUE_GAME_PLAYING_ROBOT";
	public static final String QUEUE_GAME_PLAYING_WEBSOCKET = "gambling.QUEUE_GAME_PLAYING_WEBSOCKET";
	public static final String QUEUE_GAME_BETORDER_SETTLEMENT = "gambling.QUEUE_GAME_BETORDER_SETTLEMENT";
	

	public static final String QUEUE_GAMBLING_FLOWSYNC_DRIVER = "gambling.flowsync.driver";

	public static final String EXCHANGE_FANOUT_GAME_WS = "gambling.EXCHANGE_FANOUT_GAME_WS";
	public static final String EXCHANGE_FANOUT_GAME = "gambling.EXCHANGE_FANOUT_GAME";
	public static final String EXCHANGE_TOPIC_GAME = "gambling.EXCHANGE_TOPIC_GAME";
	public static final String QUEUE_TOPIC_GAME = "gambling.QUEUE_TOPIC_GAME";
	public static final String QUEUE_FANOUT_GAME = "gambling.QUEUE_FANOUT_GAME";

	public static final String IM_CHATROOM_EXCHANGE = "im.chatroom.exchange";
	public static final String IM_CHATROOM_QUEUE_PLAYING_CLIENT = "im.chatroom.queue.playing-client";
	public static final String IM_CHATROOM_QUEUE_DEALER_CLIENT = "im.chatroom.queue.delaer-client";

	public static final String GAMBLING_QUEUE_ROBOT_ACTION = "gambling.robot.action.queue";

	private static long uniqId() {
		String nanoRandom = System.nanoTime() + "" + new Random().nextInt(99999);
		int hash = Math.abs(UUID.randomUUID().hashCode());
		int needAdd = 19 - String.valueOf(hash).length() + 1;
		return Long.valueOf(hash) + Long.valueOf(nanoRandom.substring(needAdd));
	}

	public static final String QUEUE_FANOUT_ROBOT = "gambling.ROBOT_FANOUT_QUEUE";

	public static final String EXCHANGE_FANOUT_ROBOT = "gambling.ROBOT_FANOUT_EXCHANGE";

	public static String QUEUE_FANOUT_GAME_ONLY = QUEUE_FANOUT_GAME + SplitCharacter.COLON + uniqId();
	public static String QUEUE_FANOUT_ROBOT_ONLY = GAMBLING_QUEUE_ROBOT_ACTION + SplitCharacter.COLON + uniqId();
	public static String QUEUE_FANOUT_USER_ONLY = QUEUE_FANOUT_USER + SplitCharacter.COLON + uniqId();
	public static String IM_CHATROOM_QUEUE_PLAYING_CLIENT_ONLY = IM_CHATROOM_QUEUE_PLAYING_CLIENT + SplitCharacter.COLON
			+ uniqId();

	public static final String QUEUE_FANOUT_GAME_WS = "gambling.QUEUE_FANOUT_GAME.ws";
	public static final String QUEUE_FANOUT_ROBOT_WS = "gambling.robot.action.queue.ws";
	public static final String QUEUE_FANOUT_USER_WS = "gambling.QUEUE_FANOUT_USER.ws";

	public static final String EXCHANGE_FANOUT_TODEALER = "exchange.gambling.dealer";

	public static final String EXCHANGE_FANOUT_PLAYER_EVENT = "exchange.gambling.player.event";
}
