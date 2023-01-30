package com.ampletec.gambling.report.entity;


import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Data
public class ScheduleSetting  implements Serializable {

    @Setter(AccessLevel.NONE)
    private Long id;

    private String name;

    private Integer duration;

    private Integer status;

    private Date last;

    private Boolean enable;

    @Setter(AccessLevel.NONE)
    private Date created;

    @Setter(AccessLevel.NONE)
    private Date updated;

}
