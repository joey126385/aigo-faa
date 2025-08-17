package com.ctbc.aigo.bean.dto;

import com.ctbc.aigo.bean.ResponseMessage;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author
 * @date
 * @description 錯誤回傳DTO
 **/
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "set")
public class ErrorDTO {

    // 狀態碼, 參見ResponseMessage
    @Builder.Default
    private String rspCode = "";

    // 狀態碼中文描述, 參見ResponseMessage
    @Builder.Default
    private String rspMsg = "";

    // 錯誤內容
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Object> error;

    public static class ErrorDTOBuilder {

        public ErrorDTOBuilder setResponse(ResponseMessage responseMessage) {
            this.setRspCode(responseMessage.getCode());
            this.setRspMsg(responseMessage.getDesc());
            return this;
        }
    }
}
