package com.ampletec.gambling.report.service;

import com.ampletec.gambling.report.entity.HalfHourGameWinloseReport;
import com.ampletec.gambling.report.entity.WinloseReport;
import com.ampletec.gambling.report.entity.WinloseReportStatistic;


import java.util.Date;
import java.util.List;

public interface ReportService {


    void batchInsertHalfHourGameWinloseReport(List<HalfHourGameWinloseReport> list) throws Exception;


    WinloseReportStatistic winloseReportStatisticList(Integer systemId, Date start, Date end, Long parentID, String[] currencys, String[] gameTitles) throws  Exception;

    List<WinloseReport> winloseReportList(Integer systemId,Date start, Date end, Long parentID, String[] currencys, String[] gameTitles) throws  Exception;
}
