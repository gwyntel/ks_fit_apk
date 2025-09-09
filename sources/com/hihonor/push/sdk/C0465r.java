package com.hihonor.push.sdk;

import java.util.List;

/* renamed from: com.hihonor.push.sdk.r, reason: case insensitive filesystem */
/* loaded from: classes3.dex */
public class C0465r implements k0<List<HonorPushDataMsg>> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ HonorPushCallback f15531a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ s f15532b;

    public C0465r(s sVar, HonorPushCallback honorPushCallback) {
        this.f15532b = sVar;
        this.f15531a = honorPushCallback;
    }

    @Override // com.hihonor.push.sdk.k0
    public void a(a1 a1Var) {
        if (!a1Var.e()) {
            s.a(this.f15532b, this.f15531a, -1, a1Var.b().toString());
            return;
        }
        s sVar = this.f15532b;
        HonorPushCallback honorPushCallback = this.f15531a;
        Object objC = a1Var.c();
        sVar.getClass();
        b1.a(new t(sVar, honorPushCallback, objC));
    }
}
