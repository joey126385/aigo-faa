package com.ctbc.aigo.bean.dto;

import com.ctbc.aigo.bean.ResponseMessage;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

/**
 * @author
 * @date
 * @description 回傳內容DTO
 **/
@AllArgsConstructor
@Getter
@Setter
@Data
@Builder(setterPrefix = "set")
public class ResponseDTO {

    // 狀態碼, 參見ResponseMessage
    @Builder.Default
    private String statusCode = "";

    // 狀態碼中文描述, 參見ResponseMessage
    @Builder.Default
    private String message = "";

    @JsonFormat(pattern = "yyyyMMddHHmmss")
    @Builder.Default
    private LocalDateTime rspDatetime = LocalDateTime.now();

    // 回傳內容
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    public static class ResponseDTOBuilder {
        public ResponseDTOBuilder setData(Object data) {
            this.data = data;
            var message = ResponseMessage.SUCCESS;
            if (ObjectUtils.isEmpty(data)) {
                message = ResponseMessage.NO_MATCHING_DATA;
            }
            this.setStatusCode(message.getCode());
            this.setMessage(message.getDesc());
            return this;
        }

        public ResponseDTOBuilder setResponse(ResponseMessage responseMessage) {
            this.setStatusCode(responseMessage.getCode());
            this.setMessage(responseMessage.getDesc());
            return this;
        }
    }
}
