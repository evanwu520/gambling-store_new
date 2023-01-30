package com.ampletec.gambling.report.entity.response;


import com.ampletec.gambling.report.entity.WinloseReport;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class WinloseReportResponse extends BaseResponse implements Serializable {

    @JsonProperty("TotalCount")
    private Integer totalCount = 0;

    @JsonProperty("List")
   private List<WinloseReport> list = new ArrayList<>();

}
