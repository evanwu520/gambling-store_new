package com.ampletec.cloud.thrift.client.properties;


import com.ampletec.cloud.spring.context.SpringContextBindUtil;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.HashMap;
import java.util.Map;

public class ThriftClientPropertiesCondition extends SpringBootCondition {

    private static final String SPRING_THRIFT_CLIENT = "spring.thrift.client";
    private static final String SPRING_THRIFT_CLIENT_PACKAGE_TO_SCAN = "package-to-scan";
    private static final String SPRING_THRIFT_CLIENT_SERVICE_MODEL = "service-model";

    @SuppressWarnings("unchecked")
	@Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
    	Map<String, Object> properties = SpringContextBindUtil.bind(context.getEnvironment(), HashMap.class, SPRING_THRIFT_CLIENT);

        return new ConditionOutcome(MapUtils.isNotEmpty(properties)
                && properties.containsKey(SPRING_THRIFT_CLIENT_PACKAGE_TO_SCAN)
                && StringUtils.isNotBlank(MapUtils.getString(properties, SPRING_THRIFT_CLIENT_PACKAGE_TO_SCAN)
        ), "Thrift server service model is " + MapUtils.getString(properties, SPRING_THRIFT_CLIENT_SERVICE_MODEL));

    }

}
