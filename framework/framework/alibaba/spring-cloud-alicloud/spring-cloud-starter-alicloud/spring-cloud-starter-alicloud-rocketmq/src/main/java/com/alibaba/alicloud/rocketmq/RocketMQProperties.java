package com.alibaba.alicloud.rocketmq;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.Properties;

/**
 * @author jibaole
 * @version 1.0
 * @desc 配置Bean
 * @date 2018/7/7 下午5:19
 */

@ConfigurationProperties(prefix = "spring.cloud.alicloud.rocketmq")
@Data
public class RocketMQProperties {

    private String onsAddr;

    private String nameserAddr;

    private String topic;

    private Properties producer;

    private Properties consumer;

    private String tagSuffix;
}
