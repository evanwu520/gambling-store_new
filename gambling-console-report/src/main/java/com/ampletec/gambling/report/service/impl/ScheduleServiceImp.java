package com.ampletec.gambling.report.service.impl;

import com.ampletec.gambling.report.entity.ScheduleRecord;
import com.ampletec.gambling.report.entity.ScheduleSetting;
import com.ampletec.gambling.report.mapper.ScheduleRecordMapper;
import com.ampletec.gambling.report.mapper.ScheduleSettingMapper;
import com.ampletec.gambling.report.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImp implements ScheduleService {

    @Autowired
    ScheduleRecordMapper scheduleRecordMapper;
    @Autowired
    ScheduleSettingMapper scheduleSettingMapper;

    @Override
    public ScheduleSetting findSetting(String name) throws Exception {
        return scheduleSettingMapper.findByName(name);
    }

    @Override
    public void updateSetting(ScheduleSetting setting) throws Exception  {
        scheduleSettingMapper.updateLastAndStatus(setting);
    }

    @Override
    public void insertRecord(ScheduleRecord record) throws Exception {
        scheduleRecordMapper.insert(record);
    }
}
