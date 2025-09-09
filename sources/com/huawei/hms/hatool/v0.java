package com.huawei.hms.hatool;

import android.content.Context;
import android.util.Pair;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class v0 implements g {

    /* renamed from: a, reason: collision with root package name */
    private Context f16495a = q0.i();

    /* renamed from: b, reason: collision with root package name */
    private String f16496b;

    /* renamed from: c, reason: collision with root package name */
    private String f16497c;

    /* renamed from: d, reason: collision with root package name */
    private String f16498d;

    public v0(String str, String str2, String str3) {
        this.f16496b = str;
        this.f16497c = str2;
        this.f16498d = str3;
    }

    private void a(String str, List<b1> list) {
        Pair<String, String> pairA = n1.a(str);
        new u(list, (String) pairA.first, (String) pairA.second, this.f16498d).a();
    }

    @Override // java.lang.Runnable
    public void run() {
        Map<String, List<b1>> mapA;
        v.c("hmsSdk", "eventReportTask is running");
        boolean zA = c0.a(this.f16495a);
        if (zA) {
            v.c("hmsSdk", "workKey is refresh,begin report all data");
            this.f16497c = "alltype";
        }
        try {
            try {
                try {
                    mapA = c1.a(this.f16495a, this.f16496b, this.f16497c);
                } catch (Exception e2) {
                    v.e("hmsSdk", "readEventRecords handData Exception:" + e2.getMessage());
                    if ("alltype".equals(this.f16497c)) {
                        d.a(this.f16495a, "stat_v2_1", new String[0]);
                        d.a(this.f16495a, "cached_v2_1", new String[0]);
                    } else {
                        String strA = n1.a(this.f16496b, this.f16497c);
                        d.a(this.f16495a, "stat_v2_1", strA);
                        d.a(this.f16495a, "cached_v2_1", strA);
                    }
                }
            } catch (IllegalArgumentException e3) {
                v.e("hmsSdk", "readEventRecords handData IllegalArgumentException:" + e3.getMessage());
                if ("alltype".equals(this.f16497c)) {
                    d.a(this.f16495a, "stat_v2_1", new String[0]);
                    d.a(this.f16495a, "cached_v2_1", new String[0]);
                } else {
                    String strA2 = n1.a(this.f16496b, this.f16497c);
                    d.a(this.f16495a, "stat_v2_1", strA2);
                    d.a(this.f16495a, "cached_v2_1", strA2);
                }
            }
            if (mapA.size() == 0) {
                v.b("hmsSdk", "no have events to report: tag:%s : type:%s", this.f16496b, this.f16497c);
                if ("alltype".equals(this.f16497c)) {
                    d.a(this.f16495a, "stat_v2_1", new String[0]);
                    d.a(this.f16495a, "cached_v2_1", new String[0]);
                    return;
                } else {
                    String strA3 = n1.a(this.f16496b, this.f16497c);
                    d.a(this.f16495a, "stat_v2_1", strA3);
                    d.a(this.f16495a, "cached_v2_1", strA3);
                    return;
                }
            }
            for (Map.Entry<String, List<b1>> entry : mapA.entrySet()) {
                a(entry.getKey(), entry.getValue());
            }
            if ("alltype".equals(this.f16497c)) {
                d.a(this.f16495a, "stat_v2_1", new String[0]);
                d.a(this.f16495a, "cached_v2_1", new String[0]);
            } else {
                String strA4 = n1.a(this.f16496b, this.f16497c);
                d.a(this.f16495a, "stat_v2_1", strA4);
                d.a(this.f16495a, "cached_v2_1", strA4);
            }
            if (zA) {
                v.c("hmsSdk", "refresh local key");
                o0.d().b();
            }
        } catch (Throwable th) {
            if ("alltype".equals(this.f16497c)) {
                d.a(this.f16495a, "stat_v2_1", new String[0]);
                d.a(this.f16495a, "cached_v2_1", new String[0]);
            } else {
                String strA5 = n1.a(this.f16496b, this.f16497c);
                d.a(this.f16495a, "stat_v2_1", strA5);
                d.a(this.f16495a, "cached_v2_1", strA5);
            }
            throw th;
        }
    }
}
