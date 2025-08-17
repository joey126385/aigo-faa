package com.ctbc.aigo.bean.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class RequestDTO {

    @NotNull
    @JsonProperty("faId")
    @Size(max = 10)
    private String faId;

    @NotNull
    @JsonProperty("custId")
    @Size(max = 20)
    private String custId;

    @JsonProperty("custName")
    @Size(max = 14)
    private String custName;


}
