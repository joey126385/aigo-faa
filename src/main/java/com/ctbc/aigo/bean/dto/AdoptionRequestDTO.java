package com.ctbc.aigo.bean.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;



@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdoptionRequestDTO {

    @NotNull
    @JsonProperty("faId")
    @Size(max = 10)
    private String faId;


    @JsonProperty("custName")
    @Size(max = 14)
    private String custName;

    @JsonProperty("custId")
    @Size(max = 20)
    private String custId;


}
