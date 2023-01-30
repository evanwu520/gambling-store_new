package com.ampletec.gambling.report.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Bet  implements Serializable {

    // 注單序號
    private String  serial;

    // 注單類型識別號
    private  Integer typeID;

    // 注單類型代碼
    private String typeCode;

    // 代理識別號
    private Long agentID;

    // 代理代碼
    private String agentCode;

    // 會員識別號
    private Long memberID;

    // 帳號
    private String accountName;

    // 遊戲產品識別號
    private Integer gameProductionID;

    // 遊戲產品代碼
    private String gameProductionCode;

    // 遊戲類型識別號
    private Integer gameTypeID;

    // 遊戲類型代碼
    private Integer gameID;

    // 遊戲代碼
    private String gameCode;

    // 遊戲群組
    private String gameGroup;

    // 遊戲局
    private String gameRound;

    // 點數類型識別號
    private Integer creditTypeID;

    // 點數類型代碼
    private String creditTypeCode;

    // 投注點數
    private BigDecimal betBP;

    // (投注)預扣點數
    private BigDecimal withholdingBP;

    // 結算點數
    private BigDecimal settleBP;

    // 結算點數
    private BigDecimal coveringBP;

    // 流水點數
    private BigDecimal turnOverBP;

    // 輸贏點數
    private BigDecimal winloseBP;

    // 取消(退還)點數
    private BigDecimal cancelBP;

    // 廢棄(恢復)點數
    private BigDecimal invalidateBP;

    // 投注類型
    private String betType;

    // 輸贏類型
    private String winType;

    // ip
    private String wagerIP;

    // 狀態
    private Integer state;

    // 狀態碼
    private String stateCode;

    // 投注時間
    private Date betDateTime;

    // 結算時間
    private Date settleDateTime;

    // 取消時間
    private Date cancelDateTime;

    // 棄局時間
    private Date invalidateDateTime;

}
