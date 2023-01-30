package com.ampletec.boot.activity.service.autoconfigure;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ampletec.boot.activity.service.ActivityService;
import com.ampletec.boot.activity.service.annotation.Activity;
import com.ampletec.boot.activity.service.exception.ActivityServiceException;
import com.ampletec.boot.activity.service.exception.ActivityServiceInstantiateException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class ActivityServiceAutoConfiguration implements ApplicationContextAware {

	private ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext = applicationContext;
	}
	
	@Bean
    @ConditionalOnMissingBean
    public ActivityServiceGroup activityServiceGroup() throws IOException {
        String[] beanNames = applicationContext.getBeanNamesForAnnotation(Activity.class);
        if (Objects.isNull(beanNames) || beanNames.length == 0) {
            log.error("Can not found any thrift service annotated with @ActivityRunner");
            throw new ActivityServiceException("Can not found any activity service");
        }

        List<ActivityService> services = Arrays.stream(beanNames).distinct().map(beanName -> {
        	ActivityService service = (ActivityService)applicationContext.getBean(beanName);
            if(Objects.nonNull(service)) {
            	return service;
            } else {
            	throw new ActivityServiceInstantiateException("Failed to get target bean from " + beanName);
            }

        }).collect(Collectors.toList());

        return new ActivityServiceGroup(services);
    }

    @Bean
    @ConditionalOnMissingBean
    public ActivityServiceBootstrap serviceBootstrap(ActivityServiceGroup serviceGroup) {
        return new ActivityServiceBootstrap(serviceGroup);
    }

}
