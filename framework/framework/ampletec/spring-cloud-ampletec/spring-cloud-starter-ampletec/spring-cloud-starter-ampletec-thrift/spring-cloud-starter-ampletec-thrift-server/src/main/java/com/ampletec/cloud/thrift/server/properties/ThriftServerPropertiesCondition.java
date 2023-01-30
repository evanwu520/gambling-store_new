package com.ampletec.cloud.thrift.server.properties;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import com.ampletec.cloud.spring.context.SpringContextBindUtil;

import java.util.HashMap;
import java.util.Map;

public class ThriftServerPropertiesCondition extends SpringBootCondition {

    private static final String SPRING_THRIFT_SERVER = "spring.thrift.server";
    private static final String SPRING_THRIFT_SERVER_SERVICE_ID = "service-id";

    @SuppressWarnings("unchecked")
	@Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Map<String, Object> properties = SpringContextBindUtil.bind(context.getEnvironment(), HashMap.class, SPRING_THRIFT_SERVER);

        return new ConditionOutcome(MapUtils.isNotEmpty(properties)
                && properties.containsKey(SPRING_THRIFT_SERVER_SERVICE_ID)
                && StringUtils.isNotBlank(MapUtils.getString(properties, SPRING_THRIFT_SERVER_SERVICE_ID)
        ),"Thrift server service id is " + MapUtils.getString(properties, SPRING_THRIFT_SERVER_SERVICE_ID));

    }
}
