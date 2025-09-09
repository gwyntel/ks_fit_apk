package com.google.android.gms.common.api.internal;

/* loaded from: classes3.dex */
final class zacr implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ com.google.android.gms.signin.internal.zak f12758a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ zact f12759b;

    zacr(zact zactVar, com.google.android.gms.signin.internal.zak zakVar) {
        this.f12759b = zactVar;
        this.f12758a = zakVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zact.c(this.f12759b, this.f12758a);
    }
}
