package com.meizu.cloud.pushsdk.f.f;

import android.content.Context;
import com.meizu.cloud.pushsdk.f.g.e;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private static final String f19649a = "b";

    /* renamed from: b, reason: collision with root package name */
    private String f19650b;

    /* renamed from: c, reason: collision with root package name */
    private String f19651c;

    /* renamed from: d, reason: collision with root package name */
    private String f19652d;

    /* renamed from: e, reason: collision with root package name */
    private int f19653e;

    /* renamed from: f, reason: collision with root package name */
    private final String f19654f = "SQLITE";

    /* renamed from: g, reason: collision with root package name */
    private final AtomicBoolean f19655g = new AtomicBoolean(false);

    /* renamed from: h, reason: collision with root package name */
    private long f19656h;

    /* renamed from: i, reason: collision with root package name */
    private final long f19657i;

    /* renamed from: j, reason: collision with root package name */
    private final long f19658j;

    /* renamed from: k, reason: collision with root package name */
    private final Context f19659k;

    public b(long j2, long j3, TimeUnit timeUnit, Context context) {
        this.f19651c = null;
        this.f19653e = 0;
        this.f19657i = timeUnit.toMillis(j2);
        this.f19658j = timeUnit.toMillis(j3);
        this.f19659k = context;
        Map mapC = c();
        if (mapC == null) {
            this.f19650b = e.a();
        } else {
            try {
                String string = mapC.get("userId").toString();
                String string2 = mapC.get("sessionId").toString();
                int iIntValue = ((Integer) mapC.get("sessionIndex")).intValue();
                this.f19650b = string;
                this.f19653e = iIntValue;
                this.f19651c = string2;
            } catch (Exception e2) {
                com.meizu.cloud.pushsdk.f.g.c.b(f19649a, "Exception occurred retrieving session info from file: %s", e2.getMessage());
            }
        }
        g();
        f();
        com.meizu.cloud.pushsdk.f.g.c.c(f19649a, "Tracker Session Object created.", new Object[0]);
    }

    private Map c() {
        return com.meizu.cloud.pushsdk.f.g.a.a("snowplow_session_vars", this.f19659k);
    }

    private boolean e() {
        return com.meizu.cloud.pushsdk.f.g.a.a("snowplow_session_vars", d(), this.f19659k);
    }

    private void f() {
        this.f19656h = System.currentTimeMillis();
    }

    private void g() {
        this.f19652d = this.f19651c;
        this.f19651c = e.a();
        this.f19653e++;
        String str = f19649a;
        com.meizu.cloud.pushsdk.f.g.c.a(str, "Session information is updated:", new Object[0]);
        com.meizu.cloud.pushsdk.f.g.c.a(str, " + Session ID: %s", this.f19651c);
        com.meizu.cloud.pushsdk.f.g.c.a(str, " + Previous Session ID: %s", this.f19652d);
        com.meizu.cloud.pushsdk.f.g.c.a(str, " + Session Index: %s", Integer.valueOf(this.f19653e));
        e();
    }

    public void a() {
        com.meizu.cloud.pushsdk.f.g.c.a(f19649a, "Checking and updating session information.", new Object[0]);
        if (e.a(this.f19656h, System.currentTimeMillis(), this.f19655g.get() ? this.f19658j : this.f19657i)) {
            return;
        }
        g();
        f();
    }

    public com.meizu.cloud.pushsdk.f.b.b b() {
        com.meizu.cloud.pushsdk.f.g.c.c(f19649a, "Getting session context...", new Object[0]);
        f();
        return new com.meizu.cloud.pushsdk.f.b.b("client_session", d());
    }

    public Map d() {
        HashMap map = new HashMap(8);
        map.put("userId", this.f19650b);
        map.put("sessionId", this.f19651c);
        map.put("previousSessionId", this.f19652d);
        map.put("sessionIndex", Integer.valueOf(this.f19653e));
        map.put("storageMechanism", "SQLITE");
        return map;
    }
}
