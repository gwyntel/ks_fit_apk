package com.huawei.hms.framework.network.grs.h;

import android.text.TextUtils;
import androidx.webkit.ProxyConfig;
import com.huawei.hms.framework.common.Logger;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private static final String f16295a = "b";

    /* renamed from: b, reason: collision with root package name */
    private static final Pattern f16296b = Pattern.compile("[0-9]*[a-z|A-Z]*[一-龥]*");

    public static String a(String str) {
        return a(str, "SHA-256");
    }

    public static String b(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        int i2 = 1;
        if (str.length() == 1) {
            return ProxyConfig.MATCH_ALL_SCHEMES;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i3 = 0; i3 < str.length(); i3++) {
            String str2 = str.charAt(i3) + "";
            if (f16296b.matcher(str2).matches()) {
                if (i2 % 2 == 0) {
                    str2 = ProxyConfig.MATCH_ALL_SCHEMES;
                }
                i2++;
            }
            stringBuffer.append(str2);
        }
        return stringBuffer.toString();
    }

    private static String a(String str, String str2) throws UnsupportedEncodingException {
        String str3;
        String str4;
        try {
        } catch (UnsupportedEncodingException unused) {
            str3 = f16295a;
            str4 = "encrypt UnsupportedEncodingException";
        }
        try {
            return a(MessageDigest.getInstance(str2).digest(str.getBytes("UTF-8")));
        } catch (NoSuchAlgorithmException unused2) {
            str3 = f16295a;
            str4 = "encrypt NoSuchAlgorithmException";
            Logger.w(str3, str4);
            return null;
        }
    }

    private static String a(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b2 : bArr) {
            String hexString = Integer.toHexString(b2 & 255);
            if (hexString.length() == 1) {
                sb.append("0");
            }
            sb.append(hexString);
        }
        return sb.toString();
    }
}
