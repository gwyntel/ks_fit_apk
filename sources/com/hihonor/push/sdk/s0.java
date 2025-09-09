package com.hihonor.push.sdk;

/* loaded from: classes3.dex */
public class s0 implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ a1 f15541a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ t0 f15542b;

    public s0(t0 t0Var, a1 a1Var) {
        this.f15542b = t0Var;
        this.f15541a = a1Var;
    }

    @Override // java.lang.Runnable
    public void run() {
        synchronized (this.f15542b.f15547c) {
            try {
                k0<TResult> k0Var = this.f15542b.f15546b;
                if (k0Var != 0) {
                    k0Var.a(this.f15541a);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
