package com.ampletec.gambling.report.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class WinloseReportRequest implements Serializable {

    @JsonProperty("ParentID")
    private Long parentID;

    @JsonProperty("Currencys")
    private String[] currencys;

    @JsonProperty("GameTitles")
    private String[] gameTitles;

    @JsonProperty("StartDateTime")
    private String startDateTime;
    @JsonProperty("EndDateTime")
    private String endDateTime;

}
