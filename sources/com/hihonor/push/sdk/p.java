package com.hihonor.push.sdk;

import java.util.concurrent.Callable;

/* loaded from: classes3.dex */
public class p implements Callable<Void> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ s f15527a;

    public p(s sVar) {
        this.f15527a = sVar;
    }

    @Override // java.util.concurrent.Callable
    public Void call() throws Exception {
        this.f15527a.f15537b.a(true);
        return null;
    }
}
