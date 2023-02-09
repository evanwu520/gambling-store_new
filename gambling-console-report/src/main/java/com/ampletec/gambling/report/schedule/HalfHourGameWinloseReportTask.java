package com.ampletec.gambling.report.schedule;


import com.ampletec.gambling.report.entity.HalfHourGameWinloseReport;
import com.ampletec.gambling.report.entity.ScheduleRecord;
import com.ampletec.gambling.report.entity.ScheduleSetting;
import com.ampletec.gambling.report.service.ReportService;
import com.ampletec.gambling.report.service.ScheduleService;
import com.ampletec.gambling.report.service.WagerService;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class HalfHourGameWinloseReportTask {

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    WagerService wagerService;

    @Autowired
    ReportService reportService;

    private final static Logger logger = LoggerFactory.getLogger(HalfHourGameWinloseReportTask.class);


    @Scheduled(cron = "0 15,45 * * * ?")
    public void HalfHourGameWinloseReportTask() {


        ScheduleSetting  setting = null;

        try {
            setting = scheduleService.findSetting("HalfHourGameWinloseReport");
            new HalfHourGameWinloseReportTask().commonHalfHourGameWinloseReportTask(scheduleService, wagerService, reportService, setting);
        }catch (Exception e) {
            logger.error("{}", e);
        }
    }

    @Scheduled(cron = "0 0 * * * ?")
    public void HalfHourGameWinloseReportDifferentTask() {


        ScheduleSetting  setting = null;

        try {
            setting = scheduleService.findSetting("HalfHourGameWinloseReportDifferentTask");
            new HalfHourGameWinloseReportTask().commonHalfHourGameWinloseReportTask(scheduleService, wagerService, reportService, setting);
        }catch (Exception e) {
            logger.error("{}", e);
        }
    }

    /**
     * commonHalfHourGameWinloseReportTask
     * @param setting
     * @throws Exception
     */
    public void commonHalfHourGameWinloseReportTask( ScheduleService scheduleService, WagerService wagerService,
                                                     ReportService reportService, ScheduleSetting setting) throws Exception{

        if (setting == null) {
            return;
        }

        if (!setting.getEnable()){
            return;
        }

        Date now = new Date();

        long minusValue =  now.getTime() -  setting.getLast().getTime();

        if (minusValue < setting.getDuration()*1000) {
            return;
        }

        // 進行中
        setting.setStatus(2);
        scheduleService.updateSetting(setting);

        long div = (long)setting.getDuration()*1000;
        // TODO 先固定30分鐘
        div = 1800*1000;

        long count = Math.floorDiv(minusValue, div);

        logger.info("{}",count);


        for (long i=1; i<=count; i++) {

            ScheduleRecord  record = null;

            try {

                Date start = new Date( setting.getLast().getTime());
                Date end = DateUtils.addSeconds(start, setting.getDuration());

                record = new ScheduleRecord();
                record.setScheduleid(setting.getId());
                record.setStatus(1);
                record.setStart(start);
                record.setEnd(end);

                logger.info("{} {}",start, end);

                long t1 = start.getTime()/1000;
                long t2 = end.getTime()/1000;

                List<HalfHourGameWinloseReport> list =  wagerService.findForHalfHourGameWinloseReport(t1, t2);

                List<HalfHourGameWinloseReport> records  = list.stream().map(p-> {
                    p.setStartdatetime(start);
                    p.setEnddatetime(DateUtils.addSeconds(end,-1));
                    return p;
                }).collect(Collectors.toList());

                // insert
                reportService.batchInsertHalfHourGameWinloseReport(records);

                setting.setLast(end);
                setting.setStatus(1);

            }catch (Exception e) {
                logger.error("{}", e);
                // 錯誤
                record.setStatus(2);
                setting.setStatus(3);

                if (record != null) {
                    // record
                    scheduleService.insertRecord(record);
                }
                // setting
                scheduleService.updateSetting(setting);

                return;
            }

            if (record != null) {
                // record
                scheduleService.insertRecord(record);
            }
            // setting
            scheduleService.updateSetting(setting);
        }
    }
}
