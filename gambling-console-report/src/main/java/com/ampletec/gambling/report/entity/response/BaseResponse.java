package com.ampletec.gambling.report.entity.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;



@Data
public class BaseResponse implements Serializable {


    @JsonProperty("MessageCode")
    private Integer messageCode = 1000;

    @JsonProperty("Message")
    private  String message = "SUCCESS";


}
