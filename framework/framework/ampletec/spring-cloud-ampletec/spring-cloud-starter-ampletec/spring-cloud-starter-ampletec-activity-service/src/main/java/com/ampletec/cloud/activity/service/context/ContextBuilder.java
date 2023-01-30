package com.ampletec.cloud.activity.service.context;

import java.io.IOException;

import com.ampletec.cloud.activity.service.ActivityService;
import com.ampletec.cloud.activity.service.properties.ActivityServiceProperties;
import com.ampletec.cloud.activity.service.wrapper.ActivityServiceWrapper;

public interface ContextBuilder {

    ContextBuilder prepare();

    ActivityService buildActivityService(ActivityServiceProperties properties, ActivityServiceWrapper serviceWrapper) throws IOException;

}
