package com.ampletec.boot.activity.service.context;

import java.io.IOException;
import java.util.Objects;

import com.ampletec.boot.activity.service.ActivityService;
import com.ampletec.boot.activity.service.properties.ActivityServiceProperties;
import com.ampletec.boot.activity.service.wrapper.ActivityServiceWrapper;

public class ActivitySimpleServiceContext implements ContextBuilder {

    private static ActivitySimpleServiceContext serviceContext;

    public static ActivitySimpleServiceContext context() {
        if (Objects.isNull(serviceContext)) {
            serviceContext = new ActivitySimpleServiceContext();
        }
        return serviceContext;
    }

    private ActivitySimpleServiceContext() {
    }
    
    private Object args;

    @Override
    public ContextBuilder prepare() {
        return context();
    }

    @Override
    public ActivityService buildActivityService(ActivityServiceProperties properties, ActivityServiceWrapper serviceWrapper) throws IOException {
        if (Objects.isNull(serviceContext)) {
            serviceContext = (ActivitySimpleServiceContext) prepare();
        }

        if (Objects.nonNull(serviceContext) && Objects.isNull(args)) {
//            serverContext.args = new TSimpleServerArgument(serviceWrappers, properties);
        }

        return null;
    }

}
