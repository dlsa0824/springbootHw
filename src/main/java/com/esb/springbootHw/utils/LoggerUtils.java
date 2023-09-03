package com.esb.springbootHw.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LoggerUtils {

    public static String stacktraceString(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String sStackTrace = sw.toString();
        sStackTrace = EscapeUtils.escapeJson(sStackTrace);
        return sStackTrace;
    }
}