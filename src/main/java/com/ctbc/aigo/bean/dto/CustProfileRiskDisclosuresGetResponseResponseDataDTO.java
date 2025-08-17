package com.ctbc.aigo.bean.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Data
@Builder(setterPrefix = "set")
public class CustProfileRiskDisclosuresGetResponseResponseDataDTO {
    @JsonProperty("custSsoUrl")
    private String custSsoUrl;
    @JsonProperty("isPiP")
    private JsonNode isPiP;
    @JsonProperty("isPiC")
    private JsonNode isPiC;
    @JsonProperty("piExpireDate")
    private JsonNode piExpireDate;
    @JsonProperty("isHighAssetP")
    private JsonNode isHighAssetP;
    @JsonProperty("isHighAssetC")
    private JsonNode isHighAssetC;
    @JsonProperty("highAssetExpire")
    private JsonNode highAssetExpire;


}
