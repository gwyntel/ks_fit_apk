package com.google.android.gms.cloudmessaging;

import android.os.Looper;
import android.os.Message;

/* loaded from: classes3.dex */
final class zzaa extends com.google.android.gms.internal.cloudmessaging.zzf {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Rpc f12668a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzaa(Rpc rpc, Looper looper) {
        super(looper);
        this.f12668a = rpc;
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        Rpc.b(this.f12668a, message);
    }
}
