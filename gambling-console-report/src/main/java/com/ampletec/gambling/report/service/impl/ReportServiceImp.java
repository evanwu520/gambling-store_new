package com.ampletec.gambling.report.service.impl;

import com.ampletec.gambling.report.entity.HalfHourGameWinloseReport;
import com.ampletec.gambling.report.entity.WinloseReport;
import com.ampletec.gambling.report.entity.WinloseReportStatistic;
import com.ampletec.gambling.report.mapper.HalfHourGameWinloseReportMapper;
import com.ampletec.gambling.report.service.ReportService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Service
public class ReportServiceImp implements ReportService {

    @Autowired
    private HalfHourGameWinloseReportMapper halfHourGameWinloseReportMapper;

    @Resource
    @Qualifier("sqlSessionFactory1")
    private SqlSessionFactory sqlSessionFactory;


    private final int BATCH = 1000;

    @Override
    public void batchInsertHalfHourGameWinloseReport(List<HalfHourGameWinloseReport> list) throws Exception {


        if (list == null || list.size() == 0) {
            return;
        }

        SqlSession batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        HalfHourGameWinloseReportMapper mapper = batchSqlSession.getMapper(HalfHourGameWinloseReportMapper.class);

        try {
            for (int index = 0; index < list.size(); index++) {

                mapper.insert(list.get(index));

                if ( index % BATCH == 0 || index == list.size() - 1) {
                    batchSqlSession.commit();
                    batchSqlSession.clearCache();
                }
            }
        } catch (Exception e) {
            batchSqlSession.rollback();
            throw e;
        } finally {
            if (batchSqlSession != null) {
                batchSqlSession.close();
            }
        }
    }

    @Override
    public WinloseReportStatistic winloseReportStatisticList(Integer systemID, Date start, Date end, Long parentID, String[] currencys, String[] gameTitles) throws Exception {
        return halfHourGameWinloseReportMapper.gameTableWinloseReportStatistic(systemID,start, end,parentID, currencys, gameTitles);
    }

    @Override
    public List<WinloseReport> winloseReportList(Integer systemID, Date start, Date end, Long parentID, String[] currencys, String[] gameTitles) throws Exception {
        return halfHourGameWinloseReportMapper.gameTableWinloseReport(systemID,start, end,parentID, currencys, gameTitles);
    }


}
