package com.ampletec.gambling.report.entity.request;

import com.ampletec.gambling.report.entity.GameTable;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SaveGameTableRequest {

    @JsonProperty("List")
    private List<GameTable> list;
}
