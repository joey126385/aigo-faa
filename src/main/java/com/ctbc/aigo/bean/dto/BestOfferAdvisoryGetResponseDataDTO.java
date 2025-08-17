package com.ctbc.aigo.bean.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Data
@Builder(setterPrefix = "set")
public class BestOfferAdvisoryGetResponseDataDTO {
    @JsonProperty("custSsoUrl")
    private String custSsoUrl;
    @JsonProperty("healthCheckUpdateReminder")
    private String healthCheckUpdateReminder;
    @JsonProperty("healthCheckUpdateReminderRemark")
    private String healthCheckUpdateReminderRemark;
    @JsonProperty("financialHealthCheckPending")
    private String financialHealthCheckPending;
    @JsonProperty("financialHealthCheckPendingRemark")
    private String financialHealthCheckPendingRemark;
    @JsonProperty("notAssetConnect")
    private String notAssetConnect;
    @JsonProperty("notAssetConnectRemark")
    private String notAssetConnectRemark;
    @JsonProperty("targetSignItem")
    private String targetSignItem;
    @JsonProperty("targetSignItemRemark")
    private String targetSignItemRemark;
}
