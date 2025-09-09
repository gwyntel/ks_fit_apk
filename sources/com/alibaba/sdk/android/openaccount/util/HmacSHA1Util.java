package com.alibaba.sdk.android.openaccount.util;

import android.util.Log;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes2.dex */
public class HmacSHA1Util {
    private static final String CHARSETNAME = "UTF-8";
    private static final String SIGNTYPE = "HmacSHA1";
    private static final String TAG = "DeviceDiagnose";

    public static String hmacSha1(String str, String str2) throws IllegalStateException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str.getBytes("UTF-8"), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(secretKeySpec);
            byte[] bArrDoFinal = mac.doFinal(str2.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (int i2 = 0; bArrDoFinal != null && i2 < bArrDoFinal.length; i2++) {
                String hexString = Integer.toHexString(bArrDoFinal[i2] & 255);
                if (hexString.length() == 1) {
                    sb.append('0');
                }
                sb.append(hexString);
            }
            return sb.toString().toUpperCase();
        } catch (Exception unused) {
            Log.e(TAG, "hmacSha1 fail");
            return "";
        }
    }
}
