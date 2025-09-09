package com.hihonor.push.sdk;

/* loaded from: classes3.dex */
public class h implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ HonorPushCallback f15498a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ l f15499b;

    public h(l lVar, HonorPushCallback honorPushCallback) {
        this.f15499b = lVar;
        this.f15498a = honorPushCallback;
    }

    @Override // java.lang.Runnable
    public void run() {
        s sVar = this.f15499b.f15515d;
        sVar.a(new o(sVar), this.f15498a);
    }
}
