package com.alibaba.fplayer.flutter_aliplayer;

import com.aliyun.alink.linksdk.securesigner.util.Utils;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes2.dex */
public class FlutterAliPlayerStringUtils {
    public static String stringToMD5(String str) {
        try {
            byte[] bArrDigest = MessageDigest.getInstance(Utils.MD5).digest(str.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder(bArrDigest.length * 2);
            for (byte b2 : bArrDigest) {
                int i2 = b2 & 255;
                if (i2 < 16) {
                    sb.append("0");
                }
                sb.append(Integer.toHexString(i2));
            }
            return sb.toString();
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
