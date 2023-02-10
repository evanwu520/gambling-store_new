package com.ampletec.gambling.report.service;

import com.ampletec.gambling.report.entity.GameTable;
import com.ampletec.gambling.report.entity.HalfHourGameWinloseReport;
import com.ampletec.gambling.report.entity.Wager;

import java.util.HashMap;
import java.util.List;

public interface GameTableService {

    List<GameTable> getGameTableList(Integer systemID) throws Exception;

     void clearGameTable(Integer systemID) throws Exception;

    void saveGameTable(List<GameTable> tableList) throws Exception;
}
