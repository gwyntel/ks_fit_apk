package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;

/* loaded from: classes3.dex */
final class zacq implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ zact f12757a;

    zacq(zact zactVar) {
        this.f12757a = zactVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f12757a.zah.zae(new ConnectionResult(4));
    }
}
