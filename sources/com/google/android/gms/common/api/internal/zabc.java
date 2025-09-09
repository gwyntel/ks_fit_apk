package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.os.Message;
import android.util.Log;

/* loaded from: classes3.dex */
final class zabc extends com.google.android.gms.internal.base.zau {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ zabe f12721a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zabc(zabe zabeVar, Looper looper) {
        super(looper);
        this.f12721a = zabeVar;
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        int i2 = message.what;
        if (i2 == 1) {
            zabe.g(this.f12721a);
            return;
        }
        if (i2 == 2) {
            zabe.f(this.f12721a);
            return;
        }
        Log.w("GoogleApiClientImpl", "Unknown message id: " + i2);
    }
}
