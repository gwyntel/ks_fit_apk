package com.aliyun.iot.aep.sdk.framework.log;

/* loaded from: classes3.dex */
public interface IALogCloud {
    void configCloudLog(String str, String str2, String str3, String str4);

    void d(String str, String str2);

    void d(String str, String str2, boolean z2);

    void e(String str, String str2);

    void e(String str, String str2, Exception exc);

    void e(String str, String str2, Exception exc, boolean z2);

    void e(String str, String str2, String str3);

    void e(String str, String str2, String str3, boolean z2);

    void e(String str, String str2, boolean z2);

    void i(String str, String str2);

    void i(String str, String str2, boolean z2);

    void setLevel(byte b2);

    void w(String str, String str2);

    void w(String str, String str2, boolean z2);
}
