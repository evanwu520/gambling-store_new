package com.ampletec.gambling.report.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Account  implements Serializable {

    private Long userId;

    private BigDecimal balance;

    private BigDecimal reCharge;

    private BigDecimal drawTotal;

    private Date updateTime;
}
