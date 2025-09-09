package com.google.android.gms.auth.api;

import com.google.android.gms.common.internal.ShowFirstParty;

@Deprecated
/* loaded from: classes3.dex */
public final class zbc {

    /* renamed from: a, reason: collision with root package name */
    protected Boolean f12660a;

    /* renamed from: b, reason: collision with root package name */
    protected String f12661b;

    public zbc() {
        this.f12660a = Boolean.FALSE;
    }

    @ShowFirstParty
    public final zbc zba(String str) {
        this.f12661b = str;
        return this;
    }

    @ShowFirstParty
    public zbc(zbd zbdVar) {
        this.f12660a = Boolean.FALSE;
        zbd.a(zbdVar);
        this.f12660a = Boolean.valueOf(zbdVar.zbc);
        this.f12661b = zbdVar.zbd;
    }
}
