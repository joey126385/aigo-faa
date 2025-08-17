package com.ctbc.aigo.bean.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Data
@Builder(setterPrefix = "set")
@NoArgsConstructor
public class BestOfferAdoptionListResponseDataDTO {

    @JsonProperty("order")
    private String order;

    @JsonProperty("custName")
    private String custName;

    @JsonProperty("custId")
    private String custId;

    @JsonProperty("custSsoUrl")
    private String custSsoUrl;

    @JsonProperty("toDoList")
    private List<toDoList> toDoList;

    @JsonProperty("opportunity")
    private List<opportunity> opportunity;


    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder(setterPrefix = "set")
    public static class toDoList {
        private String toDoUrl;
        private String toDoId;
        private String toDoName;
        private String type;
        private String expirationDate;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder(setterPrefix = "set")
    public static class opportunity {
        private String dashboardSsoUrl;
        private List<String> requirements;
    }

}
