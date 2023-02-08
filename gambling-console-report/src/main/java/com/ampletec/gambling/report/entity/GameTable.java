package com.ampletec.gambling.report.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.Date;

@Data
public class GameTable {

    @Setter(AccessLevel.NONE)
    // 流水號
    private Long sn;

    // 系統識別號
    private Integer systemid;

    // 遊戲桌識別號
    private Integer gameid;

    // 遊戲
    private Integer gametype;


    // 遊戲桌代碼
    private String gamecode;

    // 遊戲桌顯示名稱
    private String gametitle;

    @Setter(AccessLevel.NONE)
    private Date created;

    @Setter(AccessLevel.NONE)
    private Date updated;
}
