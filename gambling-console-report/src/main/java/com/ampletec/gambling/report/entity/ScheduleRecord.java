package com.ampletec.gambling.report.entity;


import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Data
public class ScheduleRecord implements Serializable {

    @Setter(AccessLevel.NONE)
    private Long id;

    private Long scheduleid;

    private Integer status;

    private Date start;

    private Date end;

    @Setter(AccessLevel.NONE)
    private Date created;

    @Setter(AccessLevel.NONE)
    private Date updated;


}
