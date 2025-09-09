package com.hihonor.push.sdk;

/* loaded from: classes3.dex */
public class j implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ HonorPushCallback f15507a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ l f15508b;

    public j(l lVar, HonorPushCallback honorPushCallback) {
        this.f15508b = lVar;
        this.f15507a = honorPushCallback;
    }

    @Override // java.lang.Runnable
    public void run() {
        s sVar = this.f15508b.f15515d;
        sVar.a(new q(sVar), this.f15507a);
    }
}
