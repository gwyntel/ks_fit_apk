package com.hihonor.push.sdk;

/* loaded from: classes3.dex */
public class f implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ HonorPushCallback f15480a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ boolean f15481b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ l f15482c;

    public f(l lVar, HonorPushCallback honorPushCallback, boolean z2) {
        this.f15482c = lVar;
        this.f15480a = honorPushCallback;
        this.f15481b = z2;
    }

    @Override // java.lang.Runnable
    public void run() {
        s sVar = this.f15482c.f15515d;
        sVar.a(new m(sVar, this.f15481b), this.f15480a);
    }
}
