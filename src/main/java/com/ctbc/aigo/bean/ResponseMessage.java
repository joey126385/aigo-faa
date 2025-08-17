package com.ctbc.aigo.bean;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

/**
 * @author
 * @date
 * @description 錯誤碼列表, 如果有要新增請在此新增
 **/
@Getter
public enum ResponseMessage {

    SUCCESS("0000", "查詢成功並返回資料"),
    NO_MATCHING_DATA("0001", "查無符合資料"),
    INSUFFICIENT_QUERY_CONDITIONS("1000", "查詢條件不足"),
    QUERY_CONDITION_FORMAT_ERROR("1001", "查詢條件格式不正確"),
    NOT_AUTHORIZED_QUERY("2000", "未授權的查詢"),
    DATA_SOURCE_ERROR("2001", "發查資料源有異常"),
    DATA_SOURCE_TIMEOUT("2002", "發查資料源逾時"),
    USER_NOT_FOUND("2003", "查無使用者"),
    ERROR_PROCESSING_KNOWLEDGE_FILE("2004", "處理知識文件中發生問題"),
    SYSTEM_ERROR("9999", "執行時發生例外或錯誤");

    final String code;
    final String desc;

    ResponseMessage(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static final Map<String, ResponseMessage> lookup = new HashMap<>();

    static {
        for (ResponseMessage d : ResponseMessage.values()) {
            lookup.put(d.getCode(), d);
        }
    }

    public static ResponseMessage get(String code) {
        return lookup.get(code);
    }
}
