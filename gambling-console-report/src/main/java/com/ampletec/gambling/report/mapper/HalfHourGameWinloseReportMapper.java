package com.ampletec.gambling.report.mapper;

import com.ampletec.gambling.report.entity.HalfHourGameWinloseReport;
import com.ampletec.gambling.report.entity.Wager;
import com.ampletec.gambling.report.entity.WinloseReport;
import com.ampletec.gambling.report.entity.WinloseReportStatistic;

import java.util.Date;
import java.util.List;

public interface HalfHourGameWinloseReportMapper {

    public void insert(HalfHourGameWinloseReport reportList) throws Exception;


    public List<WinloseReportStatistic> gameTableWinloseReportStatistic(Date start, Date end, Long parentID, String[] currencys, String[] gameTitles) throws Exception;


    public List<WinloseReport> gameTableWinloseReport(Date start, Date end, Long parentID, String[] currencys, String[] gameTitles) throws Exception;
}
