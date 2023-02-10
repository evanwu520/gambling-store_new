package com.ampletec.gambling.report.mapper;

import com.ampletec.gambling.report.entity.GameTable;

import java.util.HashMap;
import java.util.List;

public interface GameTableMapper {

    public List<GameTable> list(Integer systemID) throws Exception;


    public void delete(Integer systemID) throws Exception;

    public void insert(GameTable gameTable) throws Exception;


}
