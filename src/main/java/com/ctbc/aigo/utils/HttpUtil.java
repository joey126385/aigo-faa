package com.ctbc.aigo.utils;

import java.util.StringJoiner;

public final class HttpUtil {
    public static String getHttpSecurityIpExpression(String ipList) {
        String[] ipArr = ipList.split(",");
        StringJoiner httpSecurityExpression = new StringJoiner(" or ");
        for (String ip : ipArr) {
            httpSecurityExpression.add("hasIpAddress('" + ip + "')");
        }
        return httpSecurityExpression.toString();
    }
}
