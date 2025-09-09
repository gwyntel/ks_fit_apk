package com.hihonor.push.sdk;

import java.util.concurrent.Callable;

/* loaded from: classes3.dex */
public final class z0 implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ n0 f15579a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ Callable f15580b;

    public z0(n0 n0Var, Callable callable) {
        this.f15579a = n0Var;
        this.f15580b = callable;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            this.f15579a.a((n0) this.f15580b.call());
        } catch (Exception e2) {
            this.f15579a.a(e2);
        }
    }
}
