package com.ampletec.gambling.report.mapper;

import com.ampletec.gambling.report.entity.GameTable;

import java.util.HashMap;
import java.util.List;

public interface GameTableMapper {

    public List<GameTable> getGameTableList(Integer systemID);

    public void clearGameTable(Integer systemID);

    public void saveGameTable(Integer systemID, List<GameTable> list);
}
