package com.hihonor.push.sdk;

/* loaded from: classes3.dex */
public class g implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ HonorPushCallback f15493a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ l f15494b;

    public g(l lVar, HonorPushCallback honorPushCallback) {
        this.f15494b = lVar;
        this.f15493a = honorPushCallback;
    }

    @Override // java.lang.Runnable
    public void run() {
        s sVar = this.f15494b.f15515d;
        sVar.a(new n(sVar), this.f15493a);
    }
}
