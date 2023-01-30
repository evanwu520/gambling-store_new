package com.ampletec.gambling.report.mapper;

import com.ampletec.gambling.report.entity.ScheduleSetting;

public interface ScheduleSettingMapper {

    public ScheduleSetting findByName(String name) throws Exception;

    public void updateLastAndStatus(ScheduleSetting setting) throws Exception;
}
