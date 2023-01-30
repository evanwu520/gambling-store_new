package com.ampletec.boot.activity.service.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ampletec.boot.activity.service.ActivityService;
import com.ampletec.boot.activity.service.properties.ActivityServiceProperties;
import com.ampletec.boot.activity.service.wrapper.ActivityServiceWrapper;

import java.io.IOException;

public class ActivityServiceContext extends AbstractActivityServiceContext {

    private Logger log = LoggerFactory.getLogger(getClass());

    private ActivitySimpleServiceContext simpleServerContext;

    public ActivityServiceContext(ActivityServiceProperties properties, ActivityServiceWrapper serviceWrapper) {
        this.properties = properties;
        this.serviceWrapper = serviceWrapper;

        contextInitializing();
    }

    private void contextInitializing() {
        this.simpleServerContext = ActivitySimpleServiceContext.context();
    }

    @Override
    protected ActivityService buildSimpleService() throws IOException {
        log.info("Build activity service from SimpleServerContext");
        return simpleServerContext.buildActivityService(properties, serviceWrapper);
    }

    @Override
    protected ActivityService buildNonBlockingService() throws IOException {
        log.info("Build activity service from NonBlockingServerContext");
        return null;
    }

    @Override
    protected ActivityService buildThreadPoolService() throws IOException {
        log.info("Build activity service from ThreadedPoolServerContext");
        return null;
    }

    @Override
    protected ActivityService buildHsHaService() throws IOException {
        log.info("Build thrift server from HsHaServerContext");
        return null;
    }

    @Override
    protected ActivityService buildThreadedSelectorService() throws IOException {
        log.debug("Build thrift server from ThreadedSelectorServerContext");
        return null;
    }

}
