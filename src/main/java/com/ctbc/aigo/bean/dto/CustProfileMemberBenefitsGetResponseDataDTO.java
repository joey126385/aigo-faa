package com.ctbc.aigo.bean.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Data
@Builder(setterPrefix = "set")
public class CustProfileMemberBenefitsGetResponseDataDTO {
    @JsonProperty("custSsoUrl")
    private String custSsoUrl;
    @JsonProperty("vipSegmentHist")
    private JsonNode vipSegmentHist;
    @JsonProperty("vipDegree")
    private JsonNode vipDegree;
    @JsonProperty("familyVipDegree")
    private JsonNode familyVipDegree;
    @JsonProperty("idvTagDate")
    private JsonNode idvTagDate;
    @JsonProperty("familyIdvTagDate")
    private JsonNode familyIdvTagDate;
    @JsonProperty("idvNextReviewDate")
    private JsonNode idvNextReviewDate;
    @JsonProperty("familyNextReviewDate")
    private JsonNode familyNextReviewDate;
    @JsonProperty("aumWgItems")
    private JsonNode aumWgItems;
    @JsonProperty("alumItems")
    private JsonNode alumItems;
    @JsonProperty("aumWhItems")
    private JsonNode aumWhItems;
    @JsonProperty("priorityLevel")
    private JsonNode priorityLevel;
    @JsonProperty("signedRelatives")
    private JsonNode signedRelatives;
    @JsonProperty("wealthPointThisYear")
    private JsonNode wealthPointThisYear;
    @JsonProperty("wealthPointNextYear")
    private JsonNode wealthPointNextYear;
    @JsonProperty("wealthPointExpire")
    private JsonNode wealthPointExpire;
    @JsonProperty("creditCardCount")
    private JsonNode creditCardCount;
    @JsonProperty("creditCardLimitAmt")
    private JsonNode creditCardLimitAmt;
    @JsonProperty("creditcardReward")
    private JsonNode creditcardReward;


    @JsonProperty("rewardResult")
    private JsonNode rewardResult;
    @JsonProperty("last12MonthProcess")
    private String last12MonthProcess;
    @JsonProperty("airportTrans")
    private JsonNode airportTrans;
    @JsonProperty("vipRoom")
    private JsonNode vipRoom;
    @JsonProperty("vipCal")
    private JsonNode vipCal;
    @JsonProperty("airportPark")
    private JsonNode airportPark;
    @JsonProperty("parking")
    private JsonNode parking;
    @JsonProperty("roadSideRescue")
    private JsonNode roadSideRescue;

}
