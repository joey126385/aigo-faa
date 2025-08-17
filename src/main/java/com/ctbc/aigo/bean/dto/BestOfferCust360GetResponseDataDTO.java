package com.ctbc.aigo.bean.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Data
@Builder(setterPrefix = "set")
public class BestOfferCust360GetResponseDataDTO {
    @JsonProperty("custName")
    private String custName;

    @JsonProperty("custId")
    private String custId;

    @JsonProperty("custSsoUrl")
    private String custSsoUrl;

    @JsonProperty("opportunity")
    private List<Opportunity> opportunity;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder(setterPrefix = "set")
    public static class Opportunity{
        private String fundingNeeds;
        private String fundingNeedsRemark;
        private String foreignCurrencyNeeds;
        private String foreignCurrencyNeedsRemark;
        private String insuranceNeeds;
        private String insuranceNeedsRemark;
        private String etfNeeds;
        private String etfNeedsRemark;
        private String houseFinancingNeeds;
        private String houseFinancingNeedsRemark;
        private String healthCheckNeeds;
        private String healthCheckNeedsRemark;
        private String lifeStageChanges;
        private String lifeStageChangesRemark;
    }
}

