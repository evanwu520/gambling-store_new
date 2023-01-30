package com.alibaba.alicloud.rocketmq;

import com.alibaba.alicloud.context.AliCloudProperties;
import com.alibaba.alicloud.rocketmq.consumer.RocketMQConsumer;
import com.alibaba.alicloud.rocketmq.producer.LocalTransactionCheckerImpl;
import com.alibaba.alicloud.rocketmq.producer.OrderMessageTemplate;
import com.alibaba.alicloud.rocketmq.producer.RocketMQTemplate;
import com.alibaba.alicloud.rocketmq.producer.TransactionMessageTemplate;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.bean.OrderProducerBean;
import com.aliyun.openservices.ons.api.bean.ProducerBean;
import com.aliyun.openservices.ons.api.bean.TransactionProducerBean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Properties;

/**
 * @author jibaole
 * @version 1.0
 * @desc 初始化(生成|消费)相关配置
 * @date 2018/7/7 下午5:19
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({AliCloudProperties.class, RocketMQProperties.class})
@ConditionalOnProperty(name = "spring.cloud.alicloud.rocketmq.enabled", matchIfMissing = true)
public class RocketMQAutoConfiguration {
	
    @Autowired
    private Environment environment;
	
    @Autowired
    private RocketMQProperties propConfig;

    @Autowired
    private AliCloudProperties alicloudConfig;

    @Bean(name = "producer",initMethod = "start", destroyMethod = "shutdown")
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "spring.cloud.alicloud.rocketmq.producer",value = "enabled",havingValue = "true")
    public ProducerBean producer() {
        ProducerBean producerBean = new ProducerBean();
        Properties properties = new Properties();
        log.debug("start alicloud rocketmq producer init……", 
        		propConfig.getNameserAddr(), 
        		propConfig.getProducer().getProperty("groupId"));
        properties.put(PropertyKeyConst.GROUP_ID, propConfig.getProducer().getProperty("groupId"));
        properties.put(PropertyKeyConst.AccessKey, alicloudConfig.getAccessKey());
        properties.put(PropertyKeyConst.SecretKey, alicloudConfig.getSecretKey());
        properties.put(PropertyKeyConst.NAMESRV_ADDR, propConfig.getNameserAddr());
        producerBean.setProperties(properties);
        producerBean.start();
        return producerBean;
    }
    
    @Bean(name = "orderProducer",initMethod = "start", destroyMethod = "shutdown")
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "spring.cloud.alicloud.rocketmq.producer",value = "enabled",havingValue = "true")
    public OrderProducerBean orderProducer() {
    	OrderProducerBean producerBean = new OrderProducerBean();
        Properties properties = new Properties();
        log.debug("start alicloud rocketmq orderProducer init……", 
        		propConfig.getNameserAddr(), 
        		propConfig.getProducer().getProperty("groupId"));
        properties.put(PropertyKeyConst.GROUP_ID, propConfig.getProducer().getProperty("groupId"));
        properties.put(PropertyKeyConst.AccessKey, alicloudConfig.getAccessKey());
        properties.put(PropertyKeyConst.SecretKey, alicloudConfig.getSecretKey());
        properties.put(PropertyKeyConst.NAMESRV_ADDR, propConfig.getNameserAddr());
        producerBean.setProperties(properties);
        producerBean.start();
        return producerBean;
    }
    
    @Bean(name = "transactionProducer",initMethod = "start", destroyMethod = "shutdown")
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "spring.cloud.alicloud.rocketmq.producer",value = "enabled",havingValue = "true")
    public TransactionProducerBean transactionProducer() {
    	TransactionProducerBean producerBean = new TransactionProducerBean();
        Properties properties = new Properties();
        log.debug("start alicloud rocketmq transactionProducer init……", 
        		propConfig.getNameserAddr(), 
        		propConfig.getProducer().getProperty("groupId"));
        properties.put(PropertyKeyConst.GROUP_ID, propConfig.getProducer().getProperty("groupId"));
        properties.put(PropertyKeyConst.AccessKey, alicloudConfig.getAccessKey());
        properties.put(PropertyKeyConst.SecretKey, alicloudConfig.getSecretKey());
        properties.put(PropertyKeyConst.NAMESRV_ADDR, propConfig.getNameserAddr());
        producerBean.setProperties(properties);
        //LocalTransactionCheckerImpl必须在start方法调用前设置
        producerBean.setLocalTransactionChecker(new LocalTransactionCheckerImpl(null));
        producerBean.start();
        return producerBean;
    }


    @Bean(initMethod="start", destroyMethod = "shutdown")
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "spring.cloud.alicloud.rocketmq.consumer",value = "enabled",havingValue = "true")
    public RocketMQConsumer rocketMQConsumer(){
        Properties properties = new Properties();
        log.info("inti alicloud rocketmq namesev_addr[{}] consumer[{}] ……", 
        		propConfig.getNameserAddr(), 
        		propConfig.getConsumer().getProperty("groupId"));
        properties.setProperty(PropertyKeyConst.GROUP_ID, propConfig.getConsumer().getProperty("groupId"));
        properties.setProperty(PropertyKeyConst.AccessKey, alicloudConfig.getAccessKey());
        properties.setProperty(PropertyKeyConst.SecretKey, alicloudConfig.getSecretKey());
        properties.setProperty(PropertyKeyConst.NAMESRV_ADDR, propConfig.getNameserAddr());
        return  new RocketMQConsumer(this.environment, properties);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "spring.cloud.alicloud.rocketmq.producer",value = "enabled",havingValue = "true")
    public RocketMQTemplate rocketMQTemplate(){
        RocketMQTemplate rocketMQTemplate = new RocketMQTemplate();
        return rocketMQTemplate;
    }
    
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "spring.cloud.alicloud.rocketmq.producer",value = "enabled",havingValue = "true")
    public OrderMessageTemplate orderMessageTemplate(){
    	OrderMessageTemplate orderMessageTemplate = new OrderMessageTemplate();
        return orderMessageTemplate;
    }
    
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "spring.cloud.alicloud.rocketmq.producer",value = "enabled",havingValue = "true")
    public TransactionMessageTemplate transactionMessageTemplate(){
    	TransactionMessageTemplate transactionMessageTemplate = new TransactionMessageTemplate();
        return transactionMessageTemplate;
    }
}
