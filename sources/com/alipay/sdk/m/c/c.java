package com.alipay.sdk.m.c;

import android.content.Context;
import com.alipay.sdk.m.r0.b;

/* loaded from: classes2.dex */
public class c implements com.alipay.sdk.m.b.b {

    /* renamed from: d, reason: collision with root package name */
    public static final int f9203d = 1;

    /* renamed from: a, reason: collision with root package name */
    public com.alipay.sdk.m.r0.b f9204a;

    /* renamed from: b, reason: collision with root package name */
    public boolean f9205b = false;

    /* renamed from: c, reason: collision with root package name */
    public boolean f9206c = false;

    @Override // com.alipay.sdk.m.b.b
    public String a(Context context) {
        if (context == null) {
            return null;
        }
        if (!this.f9205b) {
            com.alipay.sdk.m.r0.b bVar = new com.alipay.sdk.m.r0.b();
            this.f9204a = bVar;
            this.f9206c = bVar.a(context, (b.InterfaceC0054b<String>) null) == 1;
            this.f9205b = true;
        }
        com.alipay.sdk.m.d.a.b("getOAID", "isSupported", Boolean.valueOf(this.f9206c));
        if (this.f9206c && this.f9204a.e()) {
            return this.f9204a.b();
        }
        return null;
    }
}
