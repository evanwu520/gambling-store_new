package com.ampletec.gambling.report.entity;


import com.ampletec.gambling.report.entity.response.BaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class WinloseReportStatistic  implements Serializable {

    @JsonProperty("Currency")
    private String currency = "";

    @JsonProperty("GameTitle")
    private String gameTitle = "";

    @JsonProperty("TotalBetAmount")
    private BigDecimal totalBetAmount = new BigDecimal(0);


    @JsonProperty("TotalCommAmount")
    private BigDecimal totalCommAmount = new BigDecimal(0);

    @JsonProperty("TotalWinlost")
    private BigDecimal totalWinlost = new BigDecimal(0);

    @JsonProperty("TotalPlayers")
    private Integer totalPlayers = 0;

}
