package com.ampletec.gambling.report.service;

import com.ampletec.gambling.report.entity.HalfHourGameWinloseReport;
import com.ampletec.gambling.report.entity.Wager;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface WagerService {

    List<HashMap> findAll() throws Exception;

     void batchInsert(List<Wager> wagerList) throws Exception;

    List<HalfHourGameWinloseReport> findForHalfHourGameWinloseReport(long start, long end) throws Exception;

}
