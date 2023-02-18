package com.ampletec.gambling.report.entity.response;


import com.ampletec.gambling.report.entity.WinloseReport;
import com.ampletec.gambling.report.entity.WinloseReportStatistic;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class WinloseReportStatisticResponse extends  BaseResponse implements Serializable {

//    @JsonProperty("TotalCount")
//    private Integer totalCount = 0;
//
//    @JsonProperty("List")
//    private List<WinloseReportStatistic> list = new ArrayList<>();

    @JsonProperty("TotalBetAmount")
    private BigDecimal totalBetAmount = new BigDecimal(0);

    @JsonProperty("TotalCommAmount")
    private BigDecimal totalCommAmount = new BigDecimal(0);

    @JsonProperty("TotalWinlost")
    private BigDecimal totalWinlost = new BigDecimal(0);

    @JsonProperty("TotalPlayers")
    private Integer totalPlayers = 0;



}
