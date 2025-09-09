package com.google.android.gms.common.api.internal;

/* loaded from: classes3.dex */
final class zabn implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ int f12742a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ zabq f12743b;

    zabn(zabq zabqVar, int i2) {
        this.f12743b = zabqVar;
        this.f12742a = i2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f12743b.zaI(this.f12742a);
    }
}
