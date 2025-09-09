package com.hihonor.push.sdk;

/* loaded from: classes3.dex */
public class i implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ HonorPushCallback f15503a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ l f15504b;

    public i(l lVar, HonorPushCallback honorPushCallback) {
        this.f15504b = lVar;
        this.f15503a = honorPushCallback;
    }

    @Override // java.lang.Runnable
    public void run() {
        s sVar = this.f15504b.f15515d;
        sVar.a(new p(sVar), this.f15503a);
    }
}
