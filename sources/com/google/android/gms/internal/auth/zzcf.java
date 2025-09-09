package com.google.android.gms.internal.auth;

import android.database.ContentObserver;
import android.os.Handler;

/* loaded from: classes3.dex */
final class zzcf extends ContentObserver {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ zzcg f13002a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzcf(zzcg zzcgVar, Handler handler) {
        super(null);
        this.f13002a = zzcgVar;
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z2) {
        this.f13002a.zze();
    }
}
