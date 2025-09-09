package com.aliyun.iot.aep.sdk.framework.utils;

import com.aliyun.alink.linksdk.securesigner.util.Utils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes3.dex */
public class MD5Utils {
    public static String parseMd5(String str) {
        try {
            byte[] bArrDigest = MessageDigest.getInstance(Utils.MD5).digest(str.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b2 : bArrDigest) {
                int i2 = b2 & 255;
                if (i2 < 16) {
                    sb.append(0);
                }
                sb.append(Integer.toHexString(i2));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            return "";
        }
    }
}
