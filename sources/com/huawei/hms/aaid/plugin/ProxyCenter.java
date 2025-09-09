package com.huawei.hms.aaid.plugin;

/* loaded from: classes.dex */
public class ProxyCenter {
    private PushProxy proxy;

    private static class a {

        /* renamed from: a, reason: collision with root package name */
        private static ProxyCenter f15718a = new ProxyCenter();
    }

    private static ProxyCenter getInstance() {
        return a.f15718a;
    }

    public static PushProxy getProxy() {
        return getInstance().proxy;
    }

    public static void register(PushProxy pushProxy) {
        getInstance().proxy = pushProxy;
    }
}
