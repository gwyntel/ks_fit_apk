package com.alibaba.sdk.android.openaccount.ui.util;

/* loaded from: classes2.dex */
public class JaqClientVerifier {
    public static final int NOCAPTCHA_SERVER_CODE = 26053;
    public static boolean jaqAvailable;

    static {
        try {
            Class.forName("com.alibaba.verificationsdk.ui.VerifyActivity");
            jaqAvailable = true;
        } catch (Throwable unused) {
            jaqAvailable = false;
        }
    }
}
