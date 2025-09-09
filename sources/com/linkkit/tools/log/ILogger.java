package com.linkkit.tools.log;

/* loaded from: classes4.dex */
public interface ILogger {
    void d(String str, String str2);

    void e(String str, String str2);

    void e(String str, String str2, Throwable th);

    void i(String str, String str2);

    void llog(int i2, String str, String str2, Throwable th);

    void setLogLevel(int i2);

    void w(String str, String str2);

    void w(String str, String str2, Throwable th);
}
