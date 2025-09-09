package com.huawei.hms.push.utils.ha;

/* loaded from: classes4.dex */
public class PushAnalyticsCenter {

    /* renamed from: a, reason: collision with root package name */
    private PushBaseAnalytics f16740a;

    private static class a {

        /* renamed from: a, reason: collision with root package name */
        private static PushAnalyticsCenter f16741a = new PushAnalyticsCenter();
    }

    public static PushAnalyticsCenter getInstance() {
        return a.f16741a;
    }

    public PushBaseAnalytics getPushAnalytics() {
        return this.f16740a;
    }

    public void register(PushBaseAnalytics pushBaseAnalytics) {
        this.f16740a = pushBaseAnalytics;
    }
}
