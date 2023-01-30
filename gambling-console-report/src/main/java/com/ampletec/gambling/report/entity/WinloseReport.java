package com.ampletec.gambling.report.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Data
public class WinloseReport implements Serializable {


    @JsonProperty("ParentID")
    private String parentID;

    @JsonProperty("Currency")
    private String currency;


    @JsonProperty("Game")
    private String game;

    @JsonProperty("GameTitle")
    private String gameTitle;

    @JsonProperty("TotalBetAmount")
    private BigDecimal totalBetAmount;


    @JsonProperty("TotalCommAmount")
    private BigDecimal totalCommAmount;

    @JsonProperty("TotalWinlost")
    private BigDecimal totalWinlost;

    @JsonProperty("TotalPlayers")
    private Integer totalPlayers;

    @JsonIgnore
    private Date startDateTime;

    @JsonIgnore
    private Date endDateTime;

    @JsonProperty("StartDateTime")
    private String startDateTimeFormat;

    @JsonProperty("EndDateTime")
    private String endDateTimeFormat;


}
