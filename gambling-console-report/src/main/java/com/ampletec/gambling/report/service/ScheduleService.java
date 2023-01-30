package com.ampletec.gambling.report.service;

import com.ampletec.gambling.report.entity.ScheduleRecord;
import com.ampletec.gambling.report.entity.ScheduleSetting;

public interface ScheduleService {

    ScheduleSetting findSetting(String name) throws Exception;

     void updateSetting(ScheduleSetting setting) throws Exception;

     void insertRecord(ScheduleRecord record) throws Exception;
}
