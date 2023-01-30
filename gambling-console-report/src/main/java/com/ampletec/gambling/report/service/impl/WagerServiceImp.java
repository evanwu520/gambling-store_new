package com.ampletec.gambling.report.service.impl;

import com.ampletec.gambling.report.entity.HalfHourGameWinloseReport;
import com.ampletec.gambling.report.entity.Wager;
import com.ampletec.gambling.report.mapper.BetMapper;
import com.ampletec.gambling.report.mapper.WagerMapper;
import com.ampletec.gambling.report.service.WagerService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.apache.commons.lang3.ArrayUtils.insert;

@Service
public class WagerServiceImp implements WagerService {


    @Autowired
    private WagerMapper wagerMapper;

    @Resource
    @Qualifier("sqlSessionFactory1")
    private SqlSessionFactory sqlSessionFactory;



    @Override
    public List<HashMap> findAll() throws Exception {

        return wagerMapper.findAll();
    }


//    // TODO 如果一次新增筆數太多筆，會有效能的問題
//    @Override
//    public void batchInsert(List<Wager> wagerList) {
//        wagerMapper.batchInsert(wagerList);
//    }

    private final int BATCH = 1000;

    @Override
    public void batchInsert(List<Wager> wagerList) throws Exception{


        if (wagerList == null || wagerList.size() == 0) {
            return;
        }

        SqlSession batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        WagerMapper mapper = batchSqlSession.getMapper(WagerMapper.class);

        try {
            for (int index = 0; index < wagerList.size(); index++) {

                mapper.insert(wagerList.get(index));

                if ( index % BATCH == 0 || index == wagerList.size() - 1) {
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
    public List<HalfHourGameWinloseReport> findForHalfHourGameWinloseReport(long start, long end) throws Exception {

        return wagerMapper.findForHalfHourGameWinloseReport(start, end);
    }
}
