package com.aliyun.iot.aep.sdk.log;

import android.content.Context;
import android.os.Build;
import com.aliyun.alink.linksdk.securesigner.util.Utils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Deprecated
/* loaded from: classes3.dex */
public class DeviceUtils {
    public static String getUniqueId(Context context) {
        String str = UUID.randomUUID().toString() + Build.SERIAL;
        try {
            return toMD5(str);
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            return str;
        }
    }

    private static String toMD5(String str) {
        byte[] bArrDigest = MessageDigest.getInstance(Utils.MD5).digest(str.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b2 : bArrDigest) {
            String hexString = Integer.toHexString(b2 & 255);
            if (hexString.length() < 2) {
                sb.append(0);
            }
            sb.append(hexString);
        }
        return sb.toString();
    }
}
