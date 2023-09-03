package com.esb.springbootHw.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;

public class EscapeUtils {

    private static final HashMap m = new HashMap();
    private static final HashMap mj = new HashMap();
    static {
        // m.put(34, "&quot;"); // < - less-than
        m.put(60, "&lt;"); // < - less-than
        m.put(62, "&gt;"); // > - greater-than

        mj.put(0x22, "&quot;");
        mj.put(0x27, ";;");
        mj.put(0x0d, " ;;; ");
        mj.put(0x0a, " ;;; ");
        // mj.put(62, "&gt;");
    }

    private static boolean lowerCheck(char c) {
        if (c < 0x61 || c > 0x7a) {
            return false;
        } else {
            return true;
        }
    }

    private static boolean upperCheck(char c) {
        if (c < 0x41 || c > 0x5a) {
            return false;
        } else {
            return true;
        }
    }

    private static boolean numberCheck(char c) {
        if (c < 0x30 || c > 0x39) {
            return false;
        } else {
            return true;
        }
    }

    private static boolean symbolCheck(char c) {
        if (c == '{' || c == '}' || c == '\n' || c == ':' || c == ' ') {
            return true;
        } else {
            return false;
        }
    }

    public static boolean ecspReturnCheck(String resp) {
        for (int i = 0; i < resp.length(); ++i) {
            if (!lowerCheck(resp.charAt(i))) {
                return false;
            }
            if (!upperCheck(resp.charAt(i))) {
                return false;
            }
            if (!numberCheck(resp.charAt(i))) {
                return false;
            }
            if (!symbolCheck(resp.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String escapeHtml(String str) {
        // String str = "<script>alert(\"abc\")</script>";
        try {
            StringWriter writer = new StringWriter((int) (str.length() * 1.5));
            escape(writer, str);
            // System.out.println("encoded string is " + writer.toString());
            return writer.toString();
        } catch (IOException ioe) {
            return null;
        }
    }

    private static void escape(Writer writer, String str) throws IOException {
        int len = str.length();
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            int ascii = (int) c;
            String entityName = (String) m.get(ascii);
            if (entityName == null) {
                if (c > 0x7F) {
                    writer.write("&#");
                    writer.write(Integer.toString(c, 10));
                    writer.write(';');
                } else {
                    writer.write(c);
                }
            } else {
                writer.write(entityName);
            }
        }
    }

    public static String escapeJson(String str) {
        // String str = "<script>alert(\"abc\")</script>";
        try {
            StringWriter writer = new StringWriter((int) (str.length() * 1.5));
            escapeJ(writer, str);
            // System.out.println("encoded string is " + writer.toString());
            return writer.toString();
        } catch (IOException ioe) {
            return null;
        }
    }

    private static void escapeJ(Writer writer, String str) throws IOException {
        int len = str.length();
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            int ascii = (int) c;
            String entityName = (String) mj.get(ascii);
            if (entityName == null) {
                if (c > 0x7F) {
                    writer.write("&#");
                    writer.write(Integer.toString(c, 10));
                    writer.write(';');
                } else {
                    writer.write(c);
                }
            } else {
                writer.write(entityName);
            }
        }
    }
}
