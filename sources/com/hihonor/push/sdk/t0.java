package com.hihonor.push.sdk;

import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public final class t0<TResult> implements j0<TResult> {

    /* renamed from: a, reason: collision with root package name */
    public Executor f15545a;

    /* renamed from: b, reason: collision with root package name */
    public k0<TResult> f15546b;

    /* renamed from: c, reason: collision with root package name */
    public final Object f15547c = new Object();

    public t0(Executor executor, k0<TResult> k0Var) {
        this.f15546b = k0Var;
        this.f15545a = executor;
    }

    @Override // com.hihonor.push.sdk.j0
    public final void a(a1 a1Var) {
        this.f15545a.execute(new s0(this, a1Var));
    }
}
