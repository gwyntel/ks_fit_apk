package com.huawei.hms.hatool;

import android.content.Context;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    s0 f16321a;

    /* renamed from: b, reason: collision with root package name */
    s0 f16322b;

    /* renamed from: c, reason: collision with root package name */
    Context f16323c;

    /* renamed from: d, reason: collision with root package name */
    String f16324d;

    public b(Context context) {
        if (context != null) {
            this.f16323c = context.getApplicationContext();
        }
        this.f16321a = new s0();
        this.f16322b = new s0();
    }

    public b a(int i2, String str) {
        s0 s0Var;
        v.c("hmsSdk", "Builder.setCollectURL(int type,String collectURL) is execute.TYPE : " + i2);
        if (!p1.b(str)) {
            str = "";
        }
        if (i2 == 0) {
            s0Var = this.f16321a;
        } else {
            if (i2 != 1) {
                v.f("hmsSdk", "Builder.setCollectURL(int type,String collectURL): invalid type!");
                return this;
            }
            s0Var = this.f16322b;
        }
        s0Var.b(str);
        return this;
    }

    @Deprecated
    public b b(boolean z2) {
        v.c("hmsSdk", "Builder.setEnableSN(boolean isReportSN) is execute.");
        this.f16321a.j().b(z2);
        this.f16322b.j().b(z2);
        return this;
    }

    @Deprecated
    public b c(boolean z2) {
        v.c("hmsSdk", "Builder.setEnableUDID(boolean isReportUDID) is execute.");
        this.f16321a.j().c(z2);
        this.f16322b.j().c(z2);
        return this;
    }

    public b a(String str) {
        v.c("hmsSdk", "Builder.setAppID is execute");
        this.f16324d = str;
        return this;
    }

    @Deprecated
    public b a(boolean z2) {
        v.c("hmsSdk", "Builder.setEnableImei(boolean isReportAndroidImei) is execute.");
        this.f16321a.j().a(z2);
        this.f16322b.j().a(z2);
        return this;
    }

    public void a() {
        if (this.f16323c == null) {
            v.b("hmsSdk", "analyticsConf create(): context is null,create failed!");
            return;
        }
        v.c("hmsSdk", "Builder.create() is execute.");
        z0 z0Var = new z0("_hms_config_tag");
        z0Var.b(new s0(this.f16321a));
        z0Var.a(new s0(this.f16322b));
        m.a().a(this.f16323c);
        g0.a().a(this.f16323c);
        q.c().a(z0Var);
        m.a().a(this.f16324d);
    }
}
