package com.umeng.analytics;

import android.content.Context;
import com.umeng.analytics.pro.q;
import com.umeng.commonsdk.framework.UMLogDataProtocol;
import com.umeng.commonsdk.framework.UMSenderStateNotify;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class CoreProtocol implements UMLogDataProtocol, UMSenderStateNotify {

    /* renamed from: a, reason: collision with root package name */
    private static Context f21205a;

    private static class a {

        /* renamed from: a, reason: collision with root package name */
        private static final CoreProtocol f21206a = new CoreProtocol();

        private a() {
        }
    }

    public static CoreProtocol getInstance(Context context) {
        if (f21205a == null && context != null) {
            f21205a = context.getApplicationContext();
        }
        return a.f21206a;
    }

    @Override // com.umeng.commonsdk.framework.UMSenderStateNotify
    public void onConnectionAvailable() {
        q.a(f21205a).a();
    }

    @Override // com.umeng.commonsdk.framework.UMSenderStateNotify
    public void onSenderIdle() {
        q.a(f21205a).b();
    }

    @Override // com.umeng.commonsdk.framework.UMLogDataProtocol
    public void removeCacheData(Object obj) {
        q.a(f21205a).a(obj);
    }

    @Override // com.umeng.commonsdk.framework.UMLogDataProtocol
    public JSONObject setupReportData(long j2) {
        return q.a(f21205a).a(j2);
    }

    @Override // com.umeng.commonsdk.framework.UMLogDataProtocol
    public void workEvent(Object obj, int i2) {
        q.a(f21205a).a(obj, i2);
    }

    private CoreProtocol() {
    }
}
