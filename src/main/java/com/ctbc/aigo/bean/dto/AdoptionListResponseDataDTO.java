package com.ctbc.aigo.bean.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Data
@Builder(setterPrefix = "set")
public class AdoptionListResponseDataDTO {
    @JsonProperty("adoptionList")
    private List<adoption> adoptionList;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder(setterPrefix = "set")
    public static class adoption{
        private String custId;
        private String custName;
    }
}
