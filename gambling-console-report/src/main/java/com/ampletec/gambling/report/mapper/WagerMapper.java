package com.ampletec.gambling.report.mapper;

import com.ampletec.gambling.report.entity.HalfHourGameWinloseReport;
import com.ampletec.gambling.report.entity.Wager;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface WagerMapper {

    public List<HashMap> findAll() throws Exception;


    public void batchInsert(List<Wager> wagerList) throws Exception;


    public void insert(Wager wager) throws Exception;


    public List<HalfHourGameWinloseReport> findForHalfHourGameWinloseReport(long start, long end) throws Exception;


}
