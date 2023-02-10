package com.ampletec.gambling.report.service.impl;

import com.ampletec.gambling.report.entity.GameTable;
import com.ampletec.gambling.report.mapper.GameTableMapper;
import com.ampletec.gambling.report.service.GameTableService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GameTableServiceImp implements GameTableService {


    @Autowired
    GameTableMapper gameTableMapper;
    @Resource
    @Qualifier("sqlSessionFactory1")
    private SqlSessionFactory sqlSessionFactory;


    private final int BATCH = 1000;

    @Override
    public List<GameTable> getGameTableList(Integer systemId) throws Exception {
        return gameTableMapper.list(systemId);
    }

    @Override
    public void clearGameTable(Integer systemId) throws Exception {
        gameTableMapper.delete(systemId);
    }

    @Override
    public void saveGameTable(List<GameTable> tableList) throws Exception {


        if (tableList == null || tableList.size() == 0) {
            return;
        }

        SqlSession batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        GameTableMapper mapper = batchSqlSession.getMapper(GameTableMapper.class);

        try {
            for (int index = 0; index < tableList.size(); index++) {

                mapper.insert(tableList.get(index));

                if ( index % BATCH == 0 || index == tableList.size() - 1) {
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
}
