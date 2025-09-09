package com.facebook.internal;

/* loaded from: classes3.dex */
public class InternalSettings {
    private static final String UNITY_PREFIX = "Unity.";
    private static volatile String mCustomUserAgent;

    public static String getCustomUserAgent() {
        return mCustomUserAgent;
    }

    public static boolean isUnityApp() {
        return mCustomUserAgent != null && mCustomUserAgent.startsWith(UNITY_PREFIX);
    }

    public static void setCustomUserAgent(String str) {
        mCustomUserAgent = str;
    }
}
