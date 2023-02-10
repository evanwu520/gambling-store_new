package com.ampletec.gambling.report.entity.response;

import com.ampletec.gambling.report.entity.GameTable;
import com.ampletec.gambling.report.entity.WinloseReportStatistic;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GetGameTableListResponse extends BaseResponse  {

    @JsonProperty("List")
    private List<GameTable> list = new ArrayList<>();
}
