package com.ampletec.boot.activity.service.wrapper;

import java.util.Arrays;
import java.util.Objects;

import com.ampletec.boot.activity.service.exception.ActivityServiceInstantiateException;

public final class ActivityServiceWrapperFactory {

    public static ActivityServiceWrapper wrapper(String activityRunnerName, Object activityService, double version) {
    	ActivityServiceWrapper activityServiceWrapper;
        if (version <= -1) {
            activityServiceWrapper = new ActivityServiceWrapper(activityRunnerName, activityService.getClass(), activityService);
        } else {
            activityServiceWrapper = new ActivityServiceWrapper(activityRunnerName, activityService.getClass(), activityService, version);
        }

        Class<?> thriftServiceIface = Arrays.stream(activityService.getClass().getInterfaces())
                .filter(iface -> iface.getName().endsWith("$Iface"))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No thrift Iface found on service"));

        activityServiceWrapper.setType(activityService.getClass());

        final String signature = String.join("$", new String[]{
                thriftServiceIface.getDeclaringClass().getName(),
                String.valueOf(version)
        });

        activityServiceWrapper.setActivityServiceSignature(signature);

        return activityServiceWrapper;
    }

    public static ActivityServiceWrapper wrapper(String activityRunnerName, Class<?> type, Object activityRunner, double version) {
        if (Objects.isNull(activityRunner) || !Objects.equals(type, activityRunner.getClass())) {
            throw new ActivityServiceInstantiateException("Activity service initializing in wrong way");
        }

        ActivityServiceWrapper activityServiceWrapper;
        if (version <= -1) {
        	activityServiceWrapper = new ActivityServiceWrapper(activityRunnerName, activityRunner.getClass(), activityRunner);
        } else {
        	activityServiceWrapper = new ActivityServiceWrapper(activityRunnerName, activityRunner.getClass(), activityRunner, version);
        }

        Class<?> thriftServiceIface = Arrays.stream(type.getInterfaces())
                .filter(iface -> iface.getName().endsWith("$Iface"))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No thrift IFace found on service"));


        final String signature = String.join("$", new String[]{
                thriftServiceIface.getDeclaringClass().getName(),
                String.valueOf(version)
        });

        activityServiceWrapper.setActivityServiceSignature(signature);

        return activityServiceWrapper;
    }

}
