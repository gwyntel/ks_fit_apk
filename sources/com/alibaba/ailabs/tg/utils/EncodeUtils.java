package com.alibaba.ailabs.tg.utils;

import android.os.Build;
import android.text.Html;
import android.util.Base64;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/* loaded from: classes2.dex */
public final class EncodeUtils {
    private EncodeUtils() {
    }

    public static byte[] base64Decode(String str) {
        return Base64.decode(str, 2);
    }

    public static byte[] base64Encode(String str) {
        return base64Encode(str.getBytes());
    }

    public static String base64Encode2String(byte[] bArr) {
        return Base64.encodeToString(bArr, 2);
    }

    public static CharSequence htmlDecode(String str) {
        return Build.VERSION.SDK_INT >= 24 ? Html.fromHtml(str, 0) : Html.fromHtml(str);
    }

    public static String htmlEncode(CharSequence charSequence) {
        StringBuilder sb = new StringBuilder();
        int length = charSequence.length();
        for (int i2 = 0; i2 < length; i2++) {
            char cCharAt = charSequence.charAt(i2);
            if (cCharAt == '\"') {
                sb.append("&quot;");
            } else if (cCharAt == '<') {
                sb.append("&lt;");
            } else if (cCharAt == '>') {
                sb.append("&gt;");
            } else if (cCharAt == '&') {
                sb.append("&amp;");
            } else if (cCharAt != '\'') {
                sb.append(cCharAt);
            } else {
                sb.append("&#39;");
            }
        }
        return sb.toString();
    }

    public static String urlDecode(String str) {
        return urlDecode(str, "UTF-8");
    }

    public static String urlEncode(String str) {
        return urlEncode(str, "UTF-8");
    }

    public static byte[] base64Decode(byte[] bArr) {
        return Base64.decode(bArr, 2);
    }

    public static byte[] base64Encode(byte[] bArr) {
        return Base64.encode(bArr, 2);
    }

    public static String urlDecode(String str, String str2) {
        try {
            return URLDecoder.decode(str, str2);
        } catch (UnsupportedEncodingException e2) {
            throw new AssertionError(e2);
        }
    }

    public static String urlEncode(String str, String str2) {
        try {
            return URLEncoder.encode(str, str2);
        } catch (UnsupportedEncodingException e2) {
            throw new AssertionError(e2);
        }
    }
}
