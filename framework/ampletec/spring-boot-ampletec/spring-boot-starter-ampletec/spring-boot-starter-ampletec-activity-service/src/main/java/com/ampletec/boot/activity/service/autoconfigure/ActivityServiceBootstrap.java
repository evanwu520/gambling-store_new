package com.ampletec.boot.activity.service.autoconfigure;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.SmartLifecycle;
import org.springframework.util.CollectionUtils;

import com.ampletec.boot.activity.service.ActivityService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ActivityServiceBootstrap implements SmartLifecycle {

	private ActivityServiceGroup serviceGroup;
	
	ActivityServiceBootstrap(ActivityServiceGroup serviceGroup){
		this.serviceGroup = serviceGroup;
	}

    @Override
    public boolean isAutoStartup() {
        return true;
    }
    
    @Override
    public void stop(Runnable runnable) {
        if (isRunning()) {
            log.info("Shutting down activity servers");
            serviceGroup.getGroup().forEach(service -> {
            	service.shutdown();
            });
            if (runnable != null) {
                runnable.run();
            }
        }
    }

    @Override
    public void start() {
        if (CollectionUtils.isEmpty(serviceGroup.getGroup())) {
            return;
        }
        log.info("\n ====   Starting activity services ==== \n");
        AtomicInteger serverIndex = new AtomicInteger(0);
        serviceGroup.getGroup().forEach(service -> {
        	ActivityServiceRunner runner = new ActivityServiceRunner(service);
            new Thread(runner, "activity-service-" + serverIndex.incrementAndGet()).start();
        });
    }

    @Override
    public void stop() {
        stop(null);
    }

    @Override
    public boolean isRunning() {
        return serviceGroup.getGroup().stream()
                .anyMatch(ActivityService::isServing);
    }

    @Override
    public int getPhase() {
        return Integer.MAX_VALUE;
    }

    private static class ActivityServiceRunner implements Runnable {

        private ActivityService service;

        private Logger log = LoggerFactory.getLogger(getClass());

        ActivityServiceRunner(ActivityService service) {
            this.service = service;
        }

        @Override
        public void run() {
            if (service != null) {
                this.service.startup();
                log.info(service.isServing() ? "ActivityService started successfully" : "ActivityService failed to start");
            }
        }
    }
}
