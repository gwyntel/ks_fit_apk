package com.hihonor.push.sdk;

/* loaded from: classes3.dex */
public class n0<TResult> {

    /* renamed from: a, reason: collision with root package name */
    public final a1 f15522a = new a1();

    public void a(TResult tresult) {
        a1 a1Var = this.f15522a;
        synchronized (a1Var.f15461a) {
            try {
                if (!a1Var.f15462b) {
                    a1Var.f15462b = true;
                    a1Var.f15463c = tresult;
                    a1Var.f15461a.notifyAll();
                    a1Var.a();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void a(Exception exc) {
        a1 a1Var = this.f15522a;
        synchronized (a1Var.f15461a) {
            try {
                if (!a1Var.f15462b) {
                    a1Var.f15462b = true;
                    a1Var.f15464d = exc;
                    a1Var.f15461a.notifyAll();
                    a1Var.a();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
