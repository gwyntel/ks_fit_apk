package com.heytap.mcssdk.utils;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;

/* loaded from: classes3.dex */
public class ApkInfoUtil {
    private static final String FBE = "file";
    private static final String RO_CRYPTO_TYPE = "ro.crypto.type";

    private static String get(String str) {
        try {
            return (String) Class.forName("android.os.SystemProperties").getMethod(TmpConstant.PROPERTY_IDENTIFIER_GET, String.class).invoke(null, str);
        } catch (Exception unused) {
            return null;
        }
    }

    public static boolean isFBEVersion() {
        return FBE.equals(get(RO_CRYPTO_TYPE));
    }
}
