package com.ampletec.boot.activity.service.context;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.ampletec.boot.activity.service.ActivityService;
import com.ampletec.boot.activity.service.properties.ActivityServiceProperties;
import com.ampletec.boot.activity.service.wrapper.ActivityServiceWrapper;

public abstract class AbstractActivityServiceContext {

    protected ActivityServiceProperties properties;
    protected ActivityServiceWrapper serviceWrapper;

    private volatile ActivityService activityService;

    private final Lock activityServiceLock = new ReentrantLock();

    public ActivityService buildActivityService() throws IOException {
        if (Objects.isNull(activityService)) {
            activityServiceLock.lock();
            try {
                return Objects.isNull(activityService) ? build() : activityService;
            } finally {
                activityServiceLock.unlock();
            }
        }
        return activityService;
    }

    private ActivityService build() throws IOException {
        ActivityService aService = null;

        aService = buildSimpleService();

        activityService = Objects.isNull(aService) ? buildDefaultService() : aService;
        return getActivityService();
    }

    ActivityService getActivityService() {
        return activityService;
    }

    protected abstract ActivityService buildNonBlockingService() throws IOException;

    protected abstract ActivityService buildSimpleService() throws IOException;

    protected abstract ActivityService buildThreadPoolService() throws IOException;

    protected abstract ActivityService buildHsHaService() throws IOException;

    protected abstract ActivityService buildThreadedSelectorService() throws IOException;

    ActivityService buildDefaultService() throws IOException {
        return buildSimpleService();
    }

    protected ActivityServiceProperties getProperties() {
        return properties;
    }

    public AbstractActivityServiceContext setProperties(ActivityServiceProperties properties) {
        this.properties = properties;
        return this;
    }

    public ActivityServiceWrapper getServiceWrapper() {
        return serviceWrapper;
    }

    public AbstractActivityServiceContext setServiceWrappers(ActivityServiceWrapper serviceWrapper) {
        this.serviceWrapper = serviceWrapper;
        return this;
    }

}
