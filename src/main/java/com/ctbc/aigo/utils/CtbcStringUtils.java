package com.ctbc.aigo.utils;

import java.io.UnsupportedEncodingException;

public final class CtbcStringUtils {

    /**
     * 轉換application.properties預設ISO-8859-1的中文字to UTF-8格式, 不然Swagger-ui會亂碼
     *
     * @param convertTxt
     * @return convertResult
     */
    public static String convertISOToUTF8(String convertTxt) {

        byte[] convertTxtBytes;
        String convertResult = "";
        try {
            convertTxtBytes = convertTxt.getBytes("ISO-8859-1");
            convertResult = new String(convertTxtBytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return convertResult;
    }

}
