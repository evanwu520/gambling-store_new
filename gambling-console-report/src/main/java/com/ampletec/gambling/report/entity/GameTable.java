package com.ampletec.gambling.report.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.Date;

@Data
public class GameTable {

    @Setter(AccessLevel.NONE)
    @JsonIgnore
    // 流水號
    private Long sn;


    @JsonProperty("SystemID")
    // 系統識別號
    private Integer systemid;

    @JsonProperty("GameID")
    // 遊戲桌識別號
    private Integer gameid;


    @JsonProperty("GameType")
    // 遊戲
    private Integer gametype;

    @JsonProperty("GameCode")
    // 遊戲桌代碼
    private String gamecode;

    @JsonProperty("GameTitle")
    // 遊戲桌顯示名稱
    private String gametitle;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private Date created;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private Date updated;
}
