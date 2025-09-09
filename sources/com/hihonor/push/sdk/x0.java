package com.hihonor.push.sdk;

import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public final class x0<TResult> implements j0<TResult> {

    /* renamed from: a, reason: collision with root package name */
    public Executor f15563a;

    /* renamed from: b, reason: collision with root package name */
    public m0<TResult> f15564b;

    /* renamed from: c, reason: collision with root package name */
    public final Object f15565c = new Object();

    public x0(Executor executor, m0<TResult> m0Var) {
        this.f15564b = m0Var;
        this.f15563a = executor;
    }

    @Override // com.hihonor.push.sdk.j0
    public final void a(a1 a1Var) {
        if (a1Var.e()) {
            a1Var.d();
            this.f15563a.execute(new w0(this, a1Var));
        }
    }
}
