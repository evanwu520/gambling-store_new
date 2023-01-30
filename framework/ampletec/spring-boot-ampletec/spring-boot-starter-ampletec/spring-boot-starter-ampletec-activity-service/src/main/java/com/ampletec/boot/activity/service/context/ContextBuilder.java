package com.ampletec.boot.activity.service.context;

import java.io.IOException;

import com.ampletec.boot.activity.service.ActivityService;
import com.ampletec.boot.activity.service.properties.ActivityServiceProperties;
import com.ampletec.boot.activity.service.wrapper.ActivityServiceWrapper;

public interface ContextBuilder {

    ContextBuilder prepare();

    ActivityService buildActivityService(ActivityServiceProperties properties, ActivityServiceWrapper serviceWrapper) throws IOException;

}
