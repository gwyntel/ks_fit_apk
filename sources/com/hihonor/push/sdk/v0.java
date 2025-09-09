package com.hihonor.push.sdk;

import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public final class v0<TResult> implements j0<TResult> {

    /* renamed from: a, reason: collision with root package name */
    public Executor f15555a;

    /* renamed from: b, reason: collision with root package name */
    public l0 f15556b;

    /* renamed from: c, reason: collision with root package name */
    public final Object f15557c = new Object();

    public v0(Executor executor, l0 l0Var) {
        this.f15556b = l0Var;
        this.f15555a = executor;
    }

    @Override // com.hihonor.push.sdk.j0
    public final void a(a1 a1Var) {
        if (a1Var.e()) {
            return;
        }
        a1Var.d();
        this.f15555a.execute(new u0(this, a1Var));
    }
}
