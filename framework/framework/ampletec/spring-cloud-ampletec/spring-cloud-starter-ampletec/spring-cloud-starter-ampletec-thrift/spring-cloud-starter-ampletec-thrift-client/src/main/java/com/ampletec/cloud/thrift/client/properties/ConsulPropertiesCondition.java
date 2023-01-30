package com.ampletec.cloud.thrift.client.properties;

import com.ampletec.cloud.thrift.client.common.ThriftClientContext;
import com.ampletec.cloud.spring.context.SpringContextBindUtil;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.HashMap;
import java.util.Map;

public class ConsulPropertiesCondition extends SpringBootCondition {

    private static final String SPRING_CLOUD_CONSUL = "spring.cloud.consul";
    private static final String SPRING_CLOUD_CONSUL_HOST = "host";
    private static final String SPRING_CLOUD_CONSUL_PORT = "port";
    private static final String ADDRESS_TEMPLATE = "%s:%d";

    @SuppressWarnings("unchecked")
	@Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {        
        Map<String, Object> properties = SpringContextBindUtil.bind(context.getEnvironment(), HashMap.class, SPRING_CLOUD_CONSUL);

        final String consulAddress = String.format(ADDRESS_TEMPLATE,
                MapUtils.getString(properties, SPRING_CLOUD_CONSUL_HOST),
                MapUtils.getInteger(properties, SPRING_CLOUD_CONSUL_PORT));

        ThriftClientContext.registry(consulAddress);

        return new ConditionOutcome(MapUtils.isNotEmpty(properties)
                && properties.containsKey(SPRING_CLOUD_CONSUL_HOST)
                && StringUtils.isNotBlank(MapUtils.getString(properties, SPRING_CLOUD_CONSUL_HOST))
                && properties.containsKey(SPRING_CLOUD_CONSUL_PORT)
                && MapUtils.getInteger(properties, SPRING_CLOUD_CONSUL_PORT) > 0
                , "Consul server address is " + consulAddress);
    }

}
