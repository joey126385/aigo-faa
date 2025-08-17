package com.ctbc.aigo.bean.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Data
@Builder(setterPrefix = "set")
public class AssetPlanFinancialGoalsGetResponseDataDTO {
    @JsonProperty("custSsoUrl")
    private String custSsoUrl;
    @JsonProperty("datalist")
    private JsonNode datalist;
    @JsonProperty("referenceCode")
    private JsonNode referenceCode;
    @JsonProperty("lastUpdate")
    private JsonNode lastUpdate;
    @JsonProperty("extPolicyCheck")
    private JsonNode extPolicyCheck;


}
