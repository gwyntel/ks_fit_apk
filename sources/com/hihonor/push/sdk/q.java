package com.hihonor.push.sdk;

import java.util.concurrent.Callable;

/* loaded from: classes3.dex */
public class q implements Callable<Void> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ s f15529a;

    public q(s sVar) {
        this.f15529a = sVar;
    }

    @Override // java.util.concurrent.Callable
    public Void call() throws Exception {
        this.f15529a.f15537b.a(false);
        return null;
    }
}
