package com.aliyun.alink.linksdk.tools.log;

/* loaded from: classes2.dex */
public class HLoggerFactory {

    /* renamed from: a, reason: collision with root package name */
    private ILogger f11456a = null;

    public ILogger getInstance(String str) {
        ILogger iLogger = this.f11456a;
        return iLogger != null ? iLogger : new LogerImpl(str);
    }

    public void setLogger(ILogger iLogger) {
        this.f11456a = iLogger;
    }
}
