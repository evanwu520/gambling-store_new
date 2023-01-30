package com.ampletec.gambling.report.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Wager implements Serializable {

    // 流水號
    private Long sn;

    // 系統識別號
    private Integer systemid;

    // 注單識別號
    private Long id;

    // 注單類型
    private Integer type;

    // 代理識別號
    private Long parentid;

    // 代理代碼
    private String ploginname;

    // 會員識別號
    private Long userid;

    // 帳號
    private String loginname;

    // 遊戲
    private Integer gametype;

    // 遊戲桌識別號
    private Integer gameid;

    // 遊戲桌代碼
    private String gamecode;

    // 遊戲桌顯示名稱
    private String gametitle;

    // 遊戲群組
    private Integer gameshape;

    // 遊戲局
    private Long gameroundid;

    // 點數類型
    private Integer currency;

    // 投注點數
    private BigDecimal betamount;

    // 有效投注點數
    private BigDecimal commamount;

    // 輸贏點數
    private BigDecimal winlost;

    // 狀態
    private Integer state;

    // 投注類型
    private Integer bettype;

    // 投注點
    private String betpoint ;


    // 遊戲結果
    private String gameresult ;

    // 投注時間
    private Long createtime;

    // 結算時間
    private Long settledatetime;

    // 更新時間
    private Long updatetime;


    @Setter(AccessLevel.NONE)
    // 資料時間
    private Date datadatetime;

}
