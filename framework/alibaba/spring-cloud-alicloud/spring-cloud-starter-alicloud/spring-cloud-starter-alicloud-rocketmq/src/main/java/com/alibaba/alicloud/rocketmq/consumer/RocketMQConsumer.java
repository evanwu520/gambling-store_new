package com.alibaba.alicloud.rocketmq.consumer;

import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import com.alibaba.alicloud.rocketmq.annotation.RocketMQMessageListener;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.exception.ONSClientException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jibaole
 * @version 1.0
 * @desc 消费者
 * @date 2018/7/7 下午5:19
 */

@Slf4j
public class RocketMQConsumer implements ApplicationContextAware, SmartInitializingSingleton {
	
    private ApplicationContext applicationContext;
    
    private Properties properties;
    private Consumer consumer;
    private Environment environment;

    public RocketMQConsumer(Environment environment, Properties properties) {
        if (properties == null || properties.get(PropertyKeyConst.GROUP_ID) == null
                || properties.get(PropertyKeyConst.AccessKey) == null
                || properties.get(PropertyKeyConst.SecretKey) == null
                || properties.get(PropertyKeyConst.NAMESRV_ADDR) == null) {
            throw new ONSClientException("consumer properties not set properly.");
        }
        this.environment = environment;
        this.properties = properties;
    }

    public void start() {
        this.consumer = ONSFactory.createConsumer(properties);
        this.consumer.start();
    }

    public void shutdown() {
        if (this.consumer != null) {
            this.consumer.shutdown();
        }
    }
    
    private void registerContainer(String beanName, Object bean) {
    	Class<?> clazz = AopProxyUtils.ultimateTargetClass(bean);

        if (!RocketMQListener.class.isAssignableFrom(bean.getClass())) {
            throw new IllegalStateException(clazz + " is not instance of " + RocketMQListener.class.getName());
        }

        RocketMQMessageListener annotation = clazz.getAnnotation(RocketMQMessageListener.class);
        RocketMQListener<?> listener = (RocketMQListener<?>) bean;
        String topic = this.environment.resolvePlaceholders(annotation.topic());
        String subExpression = StringUtils.arrayToDelimitedString(annotation.tag(), " || ");
        consumer.subscribe(topic, subExpression, listener);
        
        log.info("register RocketMQMessageListener {} {} {}", beanName, topic, annotation.tag());
    }

	@Override
	public void afterSingletonsInstantiated() {
		// TODO Auto-generated method stub
		Map<String, Object> beans = this.applicationContext.getBeansWithAnnotation(RocketMQMessageListener.class);
		if (Objects.nonNull(beans)) {
            beans.forEach(this::registerContainer);
        }
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext = applicationContext;
	}
}
