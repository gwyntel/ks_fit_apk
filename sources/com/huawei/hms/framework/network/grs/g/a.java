package com.huawei.hms.framework.network.grs.g;

import android.content.Context;
import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import java.util.concurrent.Callable;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    protected d f16228a;

    /* renamed from: b, reason: collision with root package name */
    private final String f16229b;

    /* renamed from: c, reason: collision with root package name */
    private final c f16230c;

    /* renamed from: d, reason: collision with root package name */
    private final int f16231d;

    /* renamed from: e, reason: collision with root package name */
    private final Context f16232e;

    /* renamed from: f, reason: collision with root package name */
    private final String f16233f;

    /* renamed from: g, reason: collision with root package name */
    private final GrsBaseInfo f16234g;

    /* renamed from: h, reason: collision with root package name */
    private final com.huawei.hms.framework.network.grs.e.c f16235h;

    public a(String str, int i2, c cVar, Context context, String str2, GrsBaseInfo grsBaseInfo, com.huawei.hms.framework.network.grs.e.c cVar2) {
        this.f16229b = str;
        this.f16230c = cVar;
        this.f16231d = i2;
        this.f16232e = context;
        this.f16233f = str2;
        this.f16234g = grsBaseInfo;
        this.f16235h = cVar2;
    }

    public Context a() {
        return this.f16232e;
    }

    public c b() {
        return this.f16230c;
    }

    public String c() {
        return this.f16229b;
    }

    public int d() {
        return this.f16231d;
    }

    public String e() {
        return this.f16233f;
    }

    public com.huawei.hms.framework.network.grs.e.c f() {
        return this.f16235h;
    }

    public Callable<d> g() {
        return new f(this.f16229b, this.f16231d, this.f16230c, this.f16232e, this.f16233f, this.f16234g, this.f16235h);
    }
}
