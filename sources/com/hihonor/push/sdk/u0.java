package com.hihonor.push.sdk;

/* loaded from: classes3.dex */
public class u0 implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ a1 f15551a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ v0 f15552b;

    public u0(v0 v0Var, a1 a1Var) {
        this.f15552b = v0Var;
        this.f15551a = a1Var;
    }

    @Override // java.lang.Runnable
    public void run() {
        synchronized (this.f15552b.f15557c) {
            try {
                l0 l0Var = this.f15552b.f15556b;
                if (l0Var != null) {
                    this.f15551a.b();
                    ((y0) l0Var).f15568a.countDown();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
