package com.ampletec.boot.activity.service.autoconfigure;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.commons.collections4.CollectionUtils;

import com.ampletec.boot.activity.service.ActivityService;


public class ActivityServiceGroup {

    private Queue<ActivityService> services = new LinkedBlockingDeque<>();
    
    ActivityServiceGroup(ActivityService ... services) {
        if (Objects.isNull(services) || services.length == 0) {
            return;
        }

        this.services.addAll(Arrays.asList(services));
    }

    ActivityServiceGroup(List<ActivityService> services) {
        if (CollectionUtils.isEmpty(services)) {
            return;
        }

        this.services.clear();
        this.services.addAll(services);
    }

    public Queue<ActivityService> getGroup() {
        return services;
    }

    public void setGroup(Queue<ActivityService> services) {
        this.services = services;
    }

}
