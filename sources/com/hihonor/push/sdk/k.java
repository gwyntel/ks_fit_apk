package com.hihonor.push.sdk;

/* loaded from: classes3.dex */
public class k implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ HonorPushCallback f15509a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ l f15510b;

    public k(l lVar, HonorPushCallback honorPushCallback) {
        this.f15510b = lVar;
        this.f15509a = honorPushCallback;
    }

    @Override // java.lang.Runnable
    public void run() {
        s sVar = this.f15510b.f15515d;
        HonorPushCallback honorPushCallback = this.f15509a;
        sVar.getClass();
        if (honorPushCallback == null) {
            return;
        }
        a1 a1VarA = b.a(new p0(sVar.f15536a));
        C0465r c0465r = new C0465r(sVar, honorPushCallback);
        a1VarA.getClass();
        a1VarA.a(new t0(o0.f15524c.f15525a, c0465r));
    }
}
