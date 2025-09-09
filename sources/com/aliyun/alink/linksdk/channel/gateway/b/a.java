package com.aliyun.alink.linksdk.channel.gateway.b;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes2.dex */
public class a {
    public static String a(String str, String str2) throws IllegalAccessException, NoSuchAlgorithmException, IllegalArgumentException, InvocationTargetException {
        ALog.d("SignUtils", "sign() called with: signString = [" + str + "], algorithm = [" + str2 + "]");
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance(str2);
                messageDigest.update(str.getBytes("UTF-8"));
                return a(messageDigest.digest());
            } catch (Exception e2) {
                ALog.e("SignUtils", "hmacSign error, e" + e2.toString());
                e2.printStackTrace();
            }
        }
        return null;
    }

    private static final String a(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer(bArr.length);
        for (byte b2 : bArr) {
            String hexString = Integer.toHexString(b2 & 255);
            if (hexString.length() < 2) {
                stringBuffer.append(0);
            }
            stringBuffer.append(hexString);
        }
        return stringBuffer.toString();
    }
}
