package com.tencent.bugly.proguard;

import java.io.UnsupportedEncodingException;

/* loaded from: classes4.dex */
public final class bl extends m implements Cloneable {

    /* renamed from: a, reason: collision with root package name */
    public String f20964a = "";

    /* renamed from: b, reason: collision with root package name */
    public String f20965b = "";

    /* renamed from: c, reason: collision with root package name */
    public String f20966c = "";

    /* renamed from: d, reason: collision with root package name */
    public String f20967d = "";

    /* renamed from: e, reason: collision with root package name */
    public String f20968e = "";

    @Override // com.tencent.bugly.proguard.m
    public final void a(StringBuilder sb, int i2) {
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(l lVar) throws UnsupportedEncodingException {
        lVar.a(this.f20964a, 0);
        String str = this.f20965b;
        if (str != null) {
            lVar.a(str, 1);
        }
        String str2 = this.f20966c;
        if (str2 != null) {
            lVar.a(str2, 2);
        }
        String str3 = this.f20967d;
        if (str3 != null) {
            lVar.a(str3, 3);
        }
        String str4 = this.f20968e;
        if (str4 != null) {
            lVar.a(str4, 4);
        }
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(k kVar) {
        this.f20964a = kVar.b(0, true);
        this.f20965b = kVar.b(1, false);
        this.f20966c = kVar.b(2, false);
        this.f20967d = kVar.b(3, false);
        this.f20968e = kVar.b(4, false);
    }
}
