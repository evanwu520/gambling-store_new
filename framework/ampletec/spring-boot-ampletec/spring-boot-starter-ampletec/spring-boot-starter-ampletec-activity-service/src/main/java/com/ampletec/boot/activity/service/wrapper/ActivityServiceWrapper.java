package com.ampletec.boot.activity.service.wrapper;

import lombok.Data;

@Data
public class ActivityServiceWrapper {

	private String activityRunnerName;

    private String activityServiceSignature;

    private Class<?> type;

    private double version;

    private final Object activityService;

    private final static Double DEFAULT_VERSION = 1.0;

    public ActivityServiceWrapper(String activityRunnerName, Class<?> type, Object activityService) {
        this(activityRunnerName, type, activityService, DEFAULT_VERSION);
    }

    public ActivityServiceWrapper(String activityRunnerName, Class<?> type, Object activityService, double version) {
        this.activityRunnerName = activityRunnerName;
        this.type = type;
        this.activityService = activityService;
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActivityServiceWrapper that = (ActivityServiceWrapper) o;

        return activityServiceSignature != null ? activityServiceSignature.equals(that.activityServiceSignature) : that.activityServiceSignature == null;
    }

    @Override
    public int hashCode() {
        return activityServiceSignature != null ? activityServiceSignature.hashCode() : 0;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("ActivityServiceWrapper").append("{");
        builder.append("activityServiceName=").append(activityRunnerName).append(",");
        builder.append("activityServiceSignature=").append(activityServiceSignature).append(",");
        builder.append("type=").append(type).append(",");
        builder.append("version=").append(version).append(",");
        builder.append("activityService=").append(activityService);

        return builder.append("}").toString();
    }
}
