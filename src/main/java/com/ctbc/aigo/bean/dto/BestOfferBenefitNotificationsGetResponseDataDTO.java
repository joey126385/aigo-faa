package com.ctbc.aigo.bean.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Data
@Builder(setterPrefix = "set")
public class BestOfferBenefitNotificationsGetResponseDataDTO {
    @JsonProperty("custSsoUrl")
    private String custSsoUrl;
    @JsonProperty("memberExpired")
    private String memberExpired;
    @JsonProperty("memberExpiredRemark")
    private String memberExpiredRemark;
    @JsonProperty("tradingQualificationExpired")
    private String tradingQualificationExpired;
    @JsonProperty("tradingQualificationExpiredRemark")
    private String tradingQualificationExpiredRemark;

}
