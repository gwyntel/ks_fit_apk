package com.tencent.bugly.proguard;

import java.io.UnsupportedEncodingException;

/* loaded from: classes4.dex */
public final class bs extends m implements Cloneable {

    /* renamed from: a, reason: collision with root package name */
    public String f21040a = "";

    /* renamed from: b, reason: collision with root package name */
    public String f21041b = "";

    @Override // com.tencent.bugly.proguard.m
    public final void a(StringBuilder sb, int i2) {
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(l lVar) throws UnsupportedEncodingException {
        lVar.a(this.f21040a, 0);
        lVar.a(this.f21041b, 1);
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(k kVar) {
        this.f21040a = kVar.b(0, true);
        this.f21041b = kVar.b(1, true);
    }
}
