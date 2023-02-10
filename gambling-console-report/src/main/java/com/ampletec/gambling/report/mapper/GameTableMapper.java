package com.ampletec.gambling.report.mapper;

import com.ampletec.gambling.report.entity.GameTable;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GameTableMapper {

    public List<GameTable> list(@Param(value="systemID")Integer systemID) throws Exception;

    public void delete(@Param(value="systemID")Integer systemID) throws Exception;

    public void insert(GameTable gameTable) throws Exception;


}
