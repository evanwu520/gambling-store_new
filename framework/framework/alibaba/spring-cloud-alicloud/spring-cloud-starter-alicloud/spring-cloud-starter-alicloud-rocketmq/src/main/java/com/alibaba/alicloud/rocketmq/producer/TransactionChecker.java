package com.alibaba.alicloud.rocketmq.producer;

import com.alibaba.alicloud.rocketmq.event.MessageEvent;
import com.aliyun.openservices.ons.api.transaction.TransactionStatus;

/**
 * 用作LocalTransactionExecuterImpl的构造器参数,用于执行本地事务操作
 * @author hengxi
 *
 */
@FunctionalInterface
public interface TransactionChecker {
	TransactionStatus check(MessageEvent messageEvent, Long hashValue);
}
