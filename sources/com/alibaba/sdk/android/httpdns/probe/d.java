package com.alibaba.sdk.android.httpdns.probe;

/* loaded from: classes2.dex */
public final class d {

    /* renamed from: a, reason: collision with root package name */
    private static IPProbeService f8894a;

    public static synchronized IPProbeService a(b bVar) {
        try {
            if (f8894a == null) {
                e eVar = new e();
                f8894a = eVar;
                eVar.setIPListUpdateCallback(bVar);
            }
        } catch (Throwable th) {
            throw th;
        }
        return f8894a;
    }
}
