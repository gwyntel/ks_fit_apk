package com.hihonor.push.sdk;

/* loaded from: classes3.dex */
public class w0 implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ a1 f15560a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ x0 f15561b;

    public w0(x0 x0Var, a1 a1Var) {
        this.f15561b = x0Var;
        this.f15560a = a1Var;
    }

    @Override // java.lang.Runnable
    public void run() {
        synchronized (this.f15561b.f15565c) {
            try {
                Object obj = this.f15561b.f15564b;
                if (obj != null) {
                    this.f15560a.c();
                    ((y0) obj).f15568a.countDown();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
