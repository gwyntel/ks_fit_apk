package com.yc.utesdk.utils.open;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public class EmojiUtil {
    private static EmojiUtil instance;

    public static String RemoveEmoji(String str) {
        return Pattern.compile("[^ -~一-龥]").matcher(str).replaceAll("").trim();
    }

    public static EmojiUtil getInstance() {
        if (instance == null) {
            instance = new EmojiUtil();
        }
        return instance;
    }

    public String filterEmoji(String str) {
        if (!utendo(str)) {
            return str;
        }
        int length = str.length();
        StringBuilder sb = null;
        for (int i2 = 0; i2 < length; i2++) {
            char cCharAt = str.charAt(i2);
            if (!utendo(cCharAt)) {
                if (sb == null) {
                    sb = new StringBuilder(str.length());
                }
                sb.append(cCharAt);
            }
        }
        return (sb == null || sb.length() == length) ? str : sb.toString();
    }

    public String replaceEmoji(String str) {
        return Pattern.compile("[🀄-🈚]|[👦-👩]|[👦🏻-👩🏿]|[🙅🏻-🙏🏿]|[🀀-\u1f7ff]|[🤐-🧀]|[😀-🙏]|[🚀-🛶]").matcher(str).replaceAll("");
    }

    public void stringToutf8(String str) throws UnsupportedEncodingException {
        try {
            URLEncoder.encode(str, "GBK");
            URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }
    }

    public String toUtf8(String str) {
        try {
            return new String(str.getBytes("UTF-8"), "UTF-8");
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public final boolean utendo(char c2) {
        return !(c2 == 0 || c2 == '\t' || c2 == '\n' || c2 == '\r' || ((c2 >= ' ' && c2 <= 55295) || ((c2 >= 57344 && c2 <= 65533) || c2 >= 0))) || c2 == 169 || c2 == 174 || c2 == 8482 || c2 == 12336 || (c2 >= 9654 && c2 <= 10175) || c2 == 9000 || ((c2 >= 9193 && c2 <= 9210) || ((c2 >= 61440 && c2 <= 65535) || ((c2 >= 9986 && c2 <= 10160) || ((c2 >= 62977 && c2 <= 63055) || c2 == 8205 || c2 == 65039 || c2 == 11088))));
    }

    public final boolean utendo(String str) {
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            if (utendo(str.charAt(i2))) {
                return true;
            }
        }
        return false;
    }
}
